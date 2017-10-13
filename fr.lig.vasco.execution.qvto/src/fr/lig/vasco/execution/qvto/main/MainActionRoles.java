package fr.lig.vasco.execution.qvto.main;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.m2m.qvt.oml.BasicModelExtent;
import org.eclipse.m2m.qvt.oml.ExecutionContextImpl;
import org.eclipse.m2m.qvt.oml.ExecutionDiagnostic;
import org.eclipse.m2m.qvt.oml.ModelExtent;
import org.eclipse.m2m.qvt.oml.TransformationExecutor;

import ActionRoles.ActionRolesPackage;
import RoleOperations.RoleOperationsPackage;
import WebApplicationModel.WebApplicationModelPackage;

public class MainActionRoles {

	public static void main(String[] args) {

		// input and output of the M2M WebApp model TO ActionRoles model
		String pathToQVT = new String(
				"C:/Users/Mouna/Documents/PFE_LIG/Eclipse Madeling_MARS_3 2/../runtime-TestTransformation_configuration/fr.lig.vasco.transformation.M2M.ActionRoles/transforms/ActionRoles.qvto");
		String pathToWebApp = new String(
				"C:/Users/Mouna/Documents/PFE_LIG/Eclipse Madeling_MARS_3 2/../runtime-TestTransformation_configuration/fr.lig.vasco.model.webapp.patientmanagement/My.webapplicationmodel");
		String pathToRoleOperationsIn = new String(
				"C:/Users/Mouna/Documents/PFE_LIG/Eclipse Madeling_MARS_3 2/../runtime-TestTransformation_configuration/fr.lig.vasco.transformation.M2M.RoleOperations/test.roleoperations");
		String pathToActionRoles = new String(
				"C:/Users/Mouna/Documents/PFE_LIG/Eclipse Madeling_MARS_3 2/../runtime-TestTransformation_configuration/fr.lig.vasco.transformation.M2M.ActionRoles/test.actionroles");

		// Refer to an existing transformation via URI
		URI transformationURI = URI.createFileURI(pathToQVT);

		// create executor for the given transformation
		TransformationExecutor executor = new TransformationExecutor(transformationURI);

		try {
			ResourceSet set = new ResourceSetImpl();

			// create a Package Registry for the WebApplication metamodel
			set.getPackageRegistry().put(WebApplicationModelPackage.eNS_URI, WebApplicationModelPackage.eINSTANCE);

			// register the WebApplicationModel Resource Factory to the
			// ".webapplicationmodel" extension
			// to load and save an WebApplicationModel model
			set.getResourceFactoryRegistry().getExtensionToFactoryMap().put("webapplicationmodel",
					new XMIResourceFactoryImpl());

			// create a Package Registry for the RoleOperations metamodel
			set.getPackageRegistry().put(RoleOperationsPackage.eNS_URI, RoleOperationsPackage.eINSTANCE);

			// register the WebApplicationModel Resource Factory to the
			// ".webapplicationmodel" extension
			// to load and save an WebApplicationModel model
			set.getResourceFactoryRegistry().getExtensionToFactoryMap().put("roleoperations",
					new XMIResourceFactoryImpl());

			// create a Package Registry for the ActionRoles metamodel
			set.getPackageRegistry().put(ActionRolesPackage.eNS_URI, ActionRolesPackage.eINSTANCE);

			Resource inResource1 = set.getResource(URI.createFileURI(pathToWebApp), true);

			inResource1.load(Collections.EMPTY_MAP);
			EList<EObject> inObjects = inResource1.getContents();

			Resource inResource2 = set.getResource(URI.createFileURI(pathToRoleOperationsIn), true);
			
			inResource2.load(Collections.EMPTY_MAP);
			EList<EObject> inObjects2 = inResource2.getContents();

			System.out.println("*** "+inObjects);
			System.out.println("*** "+inObjects2);
			
			// create the input extent with its initial contents
			ModelExtent inputWebApp = new BasicModelExtent(inObjects);
			// create the input extent with its initial contents
			ModelExtent inputRoleOperations = new BasicModelExtent(inObjects2);
			// create an empty extent to catch the output
			ModelExtent output = new BasicModelExtent();

			// setup the execution environment details ->
			// configuration properties, logger, monitor object etc.
			ExecutionContextImpl context = new ExecutionContextImpl();
			context.setConfigProperty("keepModeling", true);

			// run the transformation assigned to the executor with the given
			// input and output and execution context -> ChangeTheWorld(in, out)
			// Remark: variable arguments count is supported
			ExecutionDiagnostic result = executor.execute(context, inputWebApp, inputRoleOperations, output);

			// check the result for success
			if (result.getSeverity() == Diagnostic.OK) {
				// the output objects got captured in the output extent
				List<EObject> outObjects = output.getContents();
				// let's persist them using a resource
				ResourceSet resourceSet2 = new ResourceSetImpl();

				// register the ActionRoles Resource Factory to the
				// ".ActionRoles" extension
				// to load and save a ActionRoles model
				resourceSet2.getResourceFactoryRegistry().getExtensionToFactoryMap().put("actionroles",
						new XMIResourceFactoryImpl());

				Resource outResource = resourceSet2.getResource(URI.createFileURI(pathToActionRoles), true);
				outResource.getContents().addAll(outObjects);

				outResource.save(Collections.emptyMap());
				System.out.println("ActionRoles model saved");

			} else {
				// turn the result diagnostic into status and send it to error
				// log
				System.out.println("\n*** " + result);
				System.out.println("*** " + result.getMessage());
				// IStatus status = BasicDiagnostic.toIStatus(result);
				// Activator.getDefault().getLog().log(status);
			}
		} catch (IOException e) {
			System.out.println("Saving failed");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
