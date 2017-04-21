/* Soot - a J*va Optimization Framework
 * Copyright (C) 1997-1999 Raja Vallee-Rai
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330,
 * Boston, MA 02111-1307, USA.
 */

/*
 * Modified by the Sable Research Group and others 1997-1999.  
 * See the 'credits' file distributed with Soot for the complete list of
 * contributors.  (Soot is distributed at http://www.sable.mcgill.ca/soot)
 */

package soot.jimple;

import soot.*;
import soot.jimple.validation.*;
import soot.options.Options;
import soot.validation.BodyValidator;
import soot.validation.ValidationException;

import java.util.ArrayList;
import java.util.List;

/** Implementation of the Body class for the Jimple IR. */
public class JimpleBody extends StmtBody
{
    private static BodyValidator[] validators;

    /**
     * Returns an array containing some validators in order to validate the JimpleBody
     * @return the array containing validators
     */
    private static BodyValidator[] getValidators() {
        if (validators == null)
        {
            validators = new BodyValidator[] {
                    IdentityStatementsValidator.v(),
                    TypesValidator.v(),
                    ReturnStatementsValidator.v(),
                    InvokeArgumentValidator.v(),
                    FieldRefValidator.v(),
                    NewValidator.v(),
                    JimpleTrapValidator.v(),
                    IdentityValidator.v()
                    //InvokeValidator.getInstance()
            };
        }
        return validators;
    }

    /**
     Construct an empty JimpleBody
     **/

    JimpleBody(SootMethod m)
    {
        super(m);
    }

    /**
     Construct an extremely empty JimpleBody, for parsing into.
     **/

    JimpleBody()
    {
    }

    /** Clones the current body, making deep copies of the contents. */
    public Object clone()
    {
        Body b = new JimpleBody(getMethod());
        b.importBodyContentsFrom(this);
        return b;
    }



    /** Make sure that the JimpleBody is well formed.  If not, throw
     *  an exception.  Right now, performs only a handful of checks.  
     */
    public void validate()
    {
        final List<ValidationException> exceptionList = new ArrayList<ValidationException>();
        validate(exceptionList);
        if (!exceptionList.isEmpty())
            throw exceptionList.get(0);
    }

    /**
     * Validates the jimple body and saves a list of all validation errors 
     * @param exceptionList the list of validation errors
     */
    public void validate(List<ValidationException> exceptionList) {
        super.validate(exceptionList);
        final boolean runAllValidators = Options.getInstance().debug() || Options.getInstance().validate();
        for (BodyValidator validator : getValidators()) {
            if (!validator.isBasicValidator() && !runAllValidators)
                continue;
            validator.validate(this, exceptionList);
        }
    }
}



