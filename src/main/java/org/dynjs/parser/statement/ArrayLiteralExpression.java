/**
 *  Copyright 2012 Douglas Campos, and individual contributors
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.dynjs.parser.statement;

import static me.qmx.jitescript.util.CodegenUtils.*;

import java.util.List;

import me.qmx.jitescript.CodeBlock;

import org.antlr.runtime.tree.Tree;
import org.dynjs.compiler.JSCompiler;
import org.dynjs.runtime.DynArray;
import org.dynjs.runtime.ExecutionContext;
import org.dynjs.runtime.PropertyDescriptor;

public class ArrayLiteralExpression extends AbstractExpression {

    private final List<Expression> exprs;

    public ArrayLiteralExpression(final Tree tree, final List<Expression> exprs) {
        super(tree);
        this.exprs = exprs;
    }

    @Override
    public CodeBlock getCodeBlock() {
        return new CodeBlock() {
            {
                newobj(p(DynArray.class));
                // array
                dup();
                // array array
                invokespecial(p(DynArray.class), "<init>", sig(void.class));
                // array
                
                int index = 0;
                
                for ( Expression each : exprs ) {
                    dup();
                    // array array
                    aload( JSCompiler.Arities.EXECUTION_CONTEXT );
                    // array array context
                    ldc( index + "" );
                    // array array context name
                    append( each.getCodeBlock() );
                    // array array context name val
                    append( jsGetValue() );
                    // array array context name val
                    invokestatic( p(PropertyDescriptor.class), "newPropertyDescriptorForObjectInitializer", sig(PropertyDescriptor.class, Object.class) );
                    // array array context name desc
                    iconst_0();
                    i2b();
                    // array array context name desc bool
                    invokevirtual(p(DynArray.class), "defineOwnProperty", sig( boolean.class, ExecutionContext.class, String.class, PropertyDescriptor.class, boolean.class ) );
                    // array bool
                    pop();
                    // array
                    ++index;
                }
                // array
            }
        };
    }

}
