import lombok.val;
import net.sakuragame.eternal.kirracore.common.KirraCoreCommon;
import org.junit.Test;

import java.util.Arrays;

public class GsonTest {

    @Test
    public void execute() {
        val intArray = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        String serialized = KirraCoreCommon.getGSON().toJson(intArray);
        System.out.println(serialized);
        int[] deserialized = KirraCoreCommon.getGSON().fromJson(serialized, int[].class);
        System.out.println(Arrays.toString(deserialized));
    }
}