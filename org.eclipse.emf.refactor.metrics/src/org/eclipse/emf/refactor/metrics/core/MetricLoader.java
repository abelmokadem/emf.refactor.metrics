package org.eclipse.emf.refactor.metrics.core;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.emf.refactor.metrics.interfaces.IMetricCalculator;

public class MetricLoader {

	public static LinkedList<Metric> loadMetrics() {
		Map<String, String> metrics = new HashMap<String, String>();
		
		metrics.put("NIH", "org.eclipse.emf.refactor.metrics.uml24.umlmodel.NIH");
		
		metrics.put("NACTM", "org.eclipse.emf.refactor.metrics.uml24.umlmodel.NACTM");
		metrics.put("NAGM", "org.eclipse.emf.refactor.metrics.uml24.umlmodel.NAGM");
		metrics.put("NASM", "org.eclipse.emf.refactor.metrics.uml24.umlmodel.NASM");
		metrics.put("NATM", "org.eclipse.emf.refactor.metrics.uml24.umlmodel.NATM");
		metrics.put("NCM", "org.eclipse.emf.refactor.metrics.uml24.umlmodel.NCM");
		metrics.put("NIM", "org.eclipse.emf.refactor.metrics.uml24.umlmodel.NIM");
		metrics.put("NNOEM", "org.eclipse.emf.refactor.metrics.uml24.umlmodel.NNOEM");
		metrics.put("NOM", "org.eclipse.emf.refactor.metrics.uml24.umlmodel.NOM");
		metrics.put("NPM", "org.eclipse.emf.refactor.metrics.uml24.umlmodel.NPM");
		metrics.put("TNME", "org.eclipse.emf.refactor.metrics.uml24.umlmodel.TNME");
		
		metrics.put("AGvsC", "org.eclipse.emf.refactor.metrics.uml24.umlmodel.AGvsC");
		metrics.put("ANA", "org.eclipse.emf.refactor.metrics.uml24.umlmodel.ANA");
		metrics.put("ASvsC", "org.eclipse.emf.refactor.metrics.uml24.umlmodel.ASvsC");
		metrics.put("ATvsC", "org.eclipse.emf.refactor.metrics.uml24.umlmodel.ATvsC");
		metrics.put("DEPvsC", "org.eclipse.emf.refactor.metrics.uml24.umlmodel.DEPvsC");
		metrics.put("GEvsC", "org.eclipse.emf.refactor.metrics.uml24.umlmodel.GEvsC");
		metrics.put("MaxDIT", "org.eclipse.emf.refactor.metrics.uml24.umlmodel.MaxDIT");
		metrics.put("MaxHAgg", "org.eclipse.emf.refactor.metrics.uml24.umlmodel.MaxHAgg");
		metrics.put("NACM", "org.eclipse.emf.refactor.metrics.uml24.umlmodel.NACM");
		metrics.put("NDEPM", "org.eclipse.emf.refactor.metrics.uml24.umlmodel.NDEPM");
		metrics.put("NTDM", "org.eclipse.emf.refactor.metrics.uml24.umlmodel.NTDM");
		metrics.put("OPvsC", "org.eclipse.emf.refactor.metrics.uml24.umlmodel.OPvsC");
		
		metrics.put("NSUPC2", "org.eclipse.emf.refactor.metrics.uml24.umlcl.NSUPC2");
		metrics.put("MaxDITC", "org.eclipse.emf.refactor.metrics.uml24.umlcl.MaxDITC");
		metrics.put("HAgg", "org.eclipse.emf.refactor.metrics.uml24.umlcl.HAgg");
		
		LinkedList<Metric> metricList = new LinkedList<Metric>();
		
		for(String metricName: metrics.keySet()) {
			try {
				String metricId = metrics.get(metricName).toLowerCase();
				metricId = metricId.replaceAll("umlmodel\\.", "");
				metricId = metricId.replaceAll("umlcl\\.", "");
				
				metricList.add(new Metric(metricName, metricName, "", "", 
						(IMetricCalculator)(ClassLoader.getSystemClassLoader().loadClass(metrics.get(metricName))).newInstance(), metricId));
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return metricList;
		
//		LinkedList<Metric> metrics = new LinkedList<Metric>();
//		IConfigurationElement[] rawMetrics = Platform.getExtensionRegistry()
//				.getConfigurationElementsFor(ExtensionPointTags.EXTENSION_POINT_NAME);
//		for (IConfigurationElement element : rawMetrics) {
//			try {
//				if (element.getName().equals(ExtensionPointTags.METRIC_TAG)) {
//					Metric metric = createMetric(element);
//					if (null != metric) {
//						metrics.add(metric);
//					}
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		java.util.Collections.sort(metrics);
//		return metrics;
	}

	private static Metric createMetric(IConfigurationElement rawMetric) {
		try {
			final String name = rawMetric.getAttribute(ExtensionPointTags.METRIC_NAME_TAG);
			final String id = rawMetric.getAttribute(ExtensionPointTags.METRIC_ID_TAG);
			final String description = rawMetric
					.getAttribute(ExtensionPointTags.METRIC_DESCRIPTION_TAG);
			final String metamodel = rawMetric
					.getAttribute(ExtensionPointTags.METRIC_METAMODEL_TAG);
			final String context = rawMetric.getAttribute(ExtensionPointTags.METRIC_CONTEXT_TAG);
			final IMetricCalculator calculateClass = (IMetricCalculator) rawMetric
					.createExecutableExtension(ExtensionPointTags.METRIC_CALCULATE_CLASS_TAG);
			return new Metric(name, description, metamodel, context, calculateClass, id);
		} catch (Throwable e) {
			e.printStackTrace();
			return null;
		}
	}

}
