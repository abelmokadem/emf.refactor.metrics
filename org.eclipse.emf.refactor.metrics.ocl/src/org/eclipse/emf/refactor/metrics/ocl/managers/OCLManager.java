package org.eclipse.emf.refactor.metrics.ocl.managers;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ocl.OCL;
import org.eclipse.ocl.ParserException;
import org.eclipse.ocl.ecore.Constraint;
import org.eclipse.ocl.ecore.EcoreEnvironmentFactory;
import org.eclipse.ocl.expressions.OCLExpression;
import org.eclipse.ocl.helper.OCLHelper;

public class OCLManager {

	public static double evaluateOCLOnContextObject(EObject contextObject, String expression) {
		double result = 0.0;

		if (contextObject == null) {
			return 0.0;
		}
		
		OCL<?, EClassifier, ?, ?, ?, ?, ?, ?, ?, Constraint, EClass, EObject> ocl = OCL
				.newInstance(EcoreEnvironmentFactory.INSTANCE);
		OCLHelper<EClassifier, ?, ?, Constraint> helper = ocl.createOCLHelper();
		
		try {
			helper.setContext(contextObject.eClass());
			OCLExpression<EClassifier> query = helper.createQuery(expression);
			Object oclResult = ocl.evaluate(contextObject, query);
			
			if (oclResult instanceof Integer) {
				result = (Integer) oclResult;
			} else if (oclResult instanceof Double) {
				result = (Double) oclResult;
			} else if (oclResult instanceof Long) {
				result = (Long) oclResult;
			} else if (oclResult instanceof Float) {
				result = (Float) oclResult;
			}

		} catch (ParserException e) {
			e.printStackTrace();
		}
		
		ocl.dispose();

		return result;
	}
}
