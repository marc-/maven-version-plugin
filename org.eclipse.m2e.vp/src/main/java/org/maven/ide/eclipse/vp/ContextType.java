package org.maven.ide.eclipse.vp;

import org.eclipse.jdt.internal.corext.template.java.JavaDocContextType;
import org.eclipse.jface.text.templates.TemplateException;
import org.eclipse.jface.text.templates.TemplateVariable;

public class ContextType extends JavaDocContextType {

    public ContextType() {
        addResolver(new VersionVariableResolver());
    }

    @Override
    protected void validateVariables(final TemplateVariable[] variables)
            throws TemplateException {
        super.validateVariables(variables);
    }
}
