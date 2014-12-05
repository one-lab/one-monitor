package org.onelab.monitor.agent.transform.asm.inserter.builder;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.onelab.monitor.agent.transform.asm.inserter.CodeInserter;
import org.onelab.monitor.agent.transform.asm.inserter.InsertPoint;

/**
 * 使用样例
 * Created by chunliangh on 14-12-5.
 */
public class PicControllerBuilder implements CodeInserterBuilder {

    @Override
    public CodeInserter build() {
        return new CodeInserter("com/jumei/pic/demo/controller/PicController", "upload",
                "(Ljavax/servlet/http/HttpServletRequest;Ljavax/servlet/http/HttpServletResponse;Lorg/springframework/web/multipart/commons/CommonsMultipartFile;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V",
                new InsertPoint("com/jumei/pic/utils/PicUtils", "upLoadPic", "(Ljava/lang/String;Lcom/jumei/pic/utils/Config;[B)Ljava/lang/String;", 1)
        ) {
            @Override
            protected void beforeMethodInsn(MethodVisitor mv) {
                mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                mv.visitLdcInsn("************************************************************");
                mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
            }

            @Override
            protected void afterMethodInsn(MethodVisitor mv) {
                mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                mv.visitLdcInsn("************************************************************");
                mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
            }
        };
    }
}
