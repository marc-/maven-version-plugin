package org.maven.ide.eclipse.vp;

import org.apache.maven.project.MavenProject;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.internal.corext.template.java.JavaDocContext;
import org.eclipse.jface.text.templates.TemplateContext;
import org.eclipse.jface.text.templates.TemplateVariableResolver;
import org.eclipse.m2e.core.MavenPlugin;
import org.eclipse.m2e.core.project.IMavenProjectFacade;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

/**
 *
 * @author maxim.chizhov
 */
public class VersionVariableResolver extends TemplateVariableResolver {

    @Override
    protected String resolve(final TemplateContext context) {
        if (context instanceof JavaDocContext) {
            final IProject project = getActiveProject();
            if (project != null) {
                IMavenProjectFacade project2 = MavenPlugin
                        .getMavenProjectRegistry().getProject(project);
                if (project2 != null) {
                    try {
                        project2.getMavenProject(new NullProgressMonitor());
                    } catch (CoreException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    final MavenProject mavenProject = project2
                            .getMavenProject();
                    if (mavenProject != null) {
                        return mavenProject.getVersion();
                    }
                }
            }
            final IMavenProjectFacade[] projects = MavenPlugin
                    .getMavenProjectRegistry().getProjects();
            for (final IMavenProjectFacade projectFacade : projects) {
                final MavenProject mavenProject = projectFacade
                        .getMavenProject();
                if (mavenProject != null) {
                    return mavenProject.getVersion();
                }
            }

            return "$version$";
        }
        return "";
    }

    private static IProject getActiveProject() {
        final IWorkbenchWindow window = PlatformUI.getWorkbench()
                .getActiveWorkbenchWindow();
        if (window == null) {
            return null;
        }
        final IWorkbenchPage activePage = window.getActivePage();
        final IFile file = (IFile) activePage.getActiveEditor()
                .getEditorInput().getAdapter(IFile.class);
        if (file == null) {
            return null;
        }
        return file.getProject();
    }

}
