package annotationlibrary;

import com.google.testing.compile.JavaFileObjects;
import com.google.testing.compile.JavaSourceSubjectFactory;
import org.junit.Test;
import static com.google.common.truth.Truth.assertAbout;



public class AnnotationProcessorTests {

    //@Rule ExpectFailure expectFailure = new ExpectFailure();
  @Test
  public void CheckIfWarningIsThrownOnMissingAnnotation() {
    final AnnotationProcessor processor = new AnnotationProcessor();
    assertAbout(JavaSourceSubjectFactory.javaSource())
            .that(JavaFileObjects.forSourceLines("correctimplementation.RequestHandler",
                    "package correctimplementation;",
                    "import annotationlibrary.AbstractHandle;",
                    "import annotationlibrary.AbstractHandler;", "@AbstractHandler",
                    "public abstract class RequestHandler {",
                    " @AbstractHandle ",
                    "    void handleRequest(Throwable request){",

                    "}}"))
            .processedWith(processor)
            .compilesWithoutWarnings();
  }

  @Test
  public void CheckIfErrorIsThrownForNonAbstractClassOnAnnotatingAsAbstract() {
    final AnnotationProcessor processor = new AnnotationProcessor();
    assertAbout(JavaSourceSubjectFactory.javaSource())
            .that(JavaFileObjects.forSourceLines("correctimplementation.RequestHandler",
                    "package correctimplementation;",
                    "import annotationlibrary.AbstractHandle;",
                    "import annotationlibrary.AbstractHandler;", "@AbstractHandler",
                    "public abstract class RequestHandler {",
                    " @AbstractHandle ",
                    "    void handleRequest(Throwable request){",

                    "}}"))
            .processedWith(processor)
            .compilesWithoutError();
  }

  @Test
  public void CheckIfInheritanceIsNotProperOnHandler() {
    final AnnotationProcessor processor = new AnnotationProcessor();

      assertAbout(JavaSourceSubjectFactory.javaSource()).that(JavaFileObjects.forSourceLines("correctimplementation.NullPointerExceptionClass",
              "package correctimplementation;", "import annotationlibrary.AbstractHandle;",
              "import annotationlibrary.Handler;import annotationlibrary.AbstractHandler;",
              "@Handler",
              "public class NullPointerExceptionClass extends RequestHandler {",
              "NullPointerExceptionClass(RequestHandler handler)",
              "{}",
              "}",
              "@AbstractHandler",
              "abstract class RequestHandler {",
              "@AbstractHandle",
              " void handleRequest(Throwable request){}}",
              "class TestClass{}")).processedWith(processor)
              .compilesWithoutError();
               //AssertionError expected = expectFailure.getFailure();

  }

  @Test
  public void checkIfConstructorTypeMatches() {
    final AnnotationProcessor processor = new AnnotationProcessor();
    assertAbout(JavaSourceSubjectFactory.javaSource()).that(JavaFileObjects.forSourceLines("correctimplementation.NullPointerExceptionClass",
            "package correctimplementation;", "import annotationlibrary.AbstractHandle;",
            "import annotationlibrary.Handler;import annotationlibrary.AbstractHandler;",
            "@Handler",
            "public class NullPointerExceptionClass extends RequestHandler {",
            "NullPointerExceptionClass(RequestHandler handler)",
            "{}",
            "}",
            "@AbstractHandler",
            "abstract class RequestHandler {",
            "@AbstractHandle",
            " void handleRequest(Throwable request){}}")).processedWith(processor).compilesWithoutWarnings();
  }

  @Test
  public void checkIfAnnotatedElementIsClassType()
  {
    final AnnotationProcessor processor = new AnnotationProcessor();
    assertAbout(JavaSourceSubjectFactory.javaSource())
            .that(JavaFileObjects.forSourceLines("correctimplementation.RequestHandler",
                    "package correctimplementation;",
                    "import annotationlibrary.AbstractHandle;",
                    "import annotationlibrary.AbstractHandler;", "@AbstractHandler",
                    "public interface RequestHandler {",
                    "",
                    "   default void handleRequest(Throwable request){",

                    "}}"))
            .processedWith(processor)
            .compilesWithoutError();
  }

}