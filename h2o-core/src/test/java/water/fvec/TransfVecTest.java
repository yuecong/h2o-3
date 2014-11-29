package water.fvec;

import org.junit.*;

import water.Scope;
import water.TestUtil;

public class TransfVecTest extends TestUtil {
  @BeforeClass static public void setup() {  stall_till_cloudsize(1); }

  // Need to move test files over
  @Test public void testAdaptTo() {
    Scope.enter();
    Frame v1 = null, v2 = null;
    try {
      v1 = parse_test_file("smalldata/junit/mixcat_train.csv");
      v2 = parse_test_file("smalldata/junit/mixcat_test.csv");
      TransfVec vv = v2.vecs()[0].adaptTo(v1.vecs()[0].domain());
      Assert.assertArrayEquals("Mapping differs",new int[]{0,1,3},vv._map);
      Assert.assertArrayEquals("Mapping differs",new String[]{"A","B","C","D"},vv.domain());
    } finally {
      Scope.exit();
    }
  }

  /** Verifies that {@link TransfVec#computeMap(String[], String[])} returns
   *  correct values. */
  @Test public void testModelMappingCall() {
    Scope.enter();
    testModelMapping(ar("A", "B", "C"), ar("A", "B", "C"), ari(0,1,2));
    testModelMapping(ar("A", "B", "C"), ar(     "B", "C"), ari(  1,2));
    testModelMapping(ar("A", "B", "C"), ar(     "B"     ), ari(  1  ));

    testModelMapping(ar("A", "B", "C"), ar("A", "B", "C", "D"), ari(0,1,2,3));
    testModelMapping(ar("A", "B", "C"), ar(     "B", "C", "D"), ari(  1,2,3));
    testModelMapping(ar("A", "B", "C"), ar(     "B",      "D"), ari(  1,  3));
    Scope.exit();
  }

  private static void testModelMapping(String[] modelDomain, String[] colDomain, int[] expectedMapping) {
    int[] mapping = new TransfVec(colDomain, modelDomain)._map;
    Assert.assertArrayEquals("Mapping differs",  expectedMapping, mapping);
  }
}
