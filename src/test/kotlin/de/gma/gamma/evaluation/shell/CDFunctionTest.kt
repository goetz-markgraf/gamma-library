package de.gma.gamma.evaluation.shell

import de.gma.gamma.evaluation.BaseEvaluationTest
import de.gma.gamma.parser.GammaException
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class CDFunctionTest : BaseEvaluationTest() {

    @Test
    fun `cd to a nonexistent directory throws a GammaException`() {
        assertThatThrownBy {
            execute("""cd "this-directory-does-not-exist-xyz"""")
        }
            .isInstanceOf(GammaException::class.java)
            .hasMessageContaining("this-directory-does-not-exist-xyz")
    }

    @Test
    fun `cd to a file path (not a directory) throws a GammaException`() {
        assertThatThrownBy {
            execute("""cd "pom.xml"""")
        }
            .isInstanceOf(GammaException::class.java)
            .hasMessageContaining("pom.xml")
    }
}
