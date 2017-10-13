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
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.resource.UMLResource;

import RoleOperations.RoleOperationsPackage;

public class MainRoleOperations {

	public static void main(String[] args) {

		// input and output of the M2M UML2 model TO RoleOperations model
//		String pathToQVT = new String(
//				"C:/Users/Mouna/Documents/PFE_LIG/Eclipse Madeling_MARS_3 2/../runtime-TestTransformation_configuration/fr.lig.vasco.transformation.M2M.RoleOperations/transforms/RoleOperations.qvto");
		String pathToQVT = new String(
				"C:/Users/Mouna/Documents/PFE_LIG/runtime-LastManStanding_configuration/fr.lig.vasco.transformation.M2M.RoleOperations/transforms/RoleOperations.qvto");
//		String pathToUML = new String(
//				"C:/Users/Mouna/Documents/PFE_LIG/Eclipse Madeling_MARS_3 2/../runtime-TestTransformation_configuration/fr.lig.vasco.model.UseCase/MedicalRecordsAC.uml");
		String pathToUML = new String(
				"C:/Users/Mouna/Documents/PFE_LIG/runtime-LastManStanding_configuration/fr.lig.vasco.model.UseCase/MedicalRecordsAC.uml");
//		String pathToRoleOperationsOut = new String(
//				"C:/Users/Mouna/Documents/PFE_LIG/Eclipse Madeling_MARS_3 2/../runtime-TestTransformation_configuration/fr.lig.vasco.transformation.M2M.RoleOperations/Test.roleoperations");

		String pathToRoleOperationsOut = new String("C:/Users/Mouna/Documents/PFE_LIG/runtime-LastManStanding_configuration/fr.lig.vasco.transformation.M2M.RoleOperations/dst_model/My1.roleoperations");
		
		// Refer to an existing transformation via URI
		URI transformationURI = URI.createFileURI(pathToQVT);

		// create executor for the given transformation
		TransformationExecutor executor = new TransformationExecutor(transformationURI);

		try {
			ResourceSet set = new ResourceSetImpl();

			// create a Package Registry for the UML2 metamodel
			set.getPackageRegistry().put(UMLPackage.eNS_URI, UMLPackage.eINSTANCE);

			// register the UML2 Resource Factory to the ".uml" extension
			// to load and save an UML2 model
			set.getResourceFactoryRegistry().getExtensionToFactoryMap().put(UMLResource.FILE_EXTENSION,
					UMLResource.Factory.INSTANCE);

			// create a Package Registry for the RoleOperations metamodel
			set.getPackageRegistry().put(RoleOperationsPackage.eNS_URI, RoleOperationsPackage.eINSTANCE);

			Resource inResource = set.getResource(URI.createFileURI(pathToUML), true);

			inResource.load(Collections.EMPTY_MAP);
			EList<EObject> inObjects = inResource.getContents();
			
			System.out.println("\n+++++ UML \n");
			for (EObject o : inObjects) {
				System.out.println(o+"\n");
			}
			

			// create the input extent with its initial contents
			ModelExtent input = new BasicModelExtent(inObjects);
			// create an empty extent to catch the output
			ModelExtent output = new BasicModelExtent();

			// setup the execution environment details ->
			// configuration properties, logger, monitor object etc.
			ExecutionContextImpl context = new ExecutionContextImpl();
			context.setConfigProperty("keepModeling", true);

			// run the transformation assigned to the executor with the given
			// input and output and execution context -> ChangeTheWorld(in, out)
			// Remark: variable arguments count is supported
			ExecutionDiagnostic result = executor.execute(context, input, output);

			// check the result for success
			if (result.getSeverity() == Diagnostic.OK) {
				// the output objects got captured in the output extent
				List<EObject> outObjects = output.getContents();
				// let's persist them using a resource
				ResourceSet resourceSet2 = new ResourceSetImpl();

				System.out.println("\n+++++ RoleOperatios \n");
				for (EObject o : outObjects) {
					System.out.println(o+"\n");
				}
				
				// register the RoleOperations Resource Factory to the
				// ".RoleOperations" extension
				// to load and save a RoleOperations model
				resourceSet2.getResourceFactoryRegistry().getExtensionToFactoryMap().put("roleoperations",
						new XMIResourceFactoryImpl());

				Resource outResource = resourceSet2.getResource(URI.createFileURI(pathToRoleOperationsOut), true);
				outResource.getContents().addAll(outObjects);

				outResource.save(Collections.emptyMap());
				System.out.println("RoleOperations model saved");

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
