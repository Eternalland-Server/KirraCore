import net.sakuragame.eternal.kirracore.bukkit.compat.mythicmobs.CompatMythicMobsHandler;
import org.junit.Test;

import java.util.ArrayList;

public class ClassTest {

    @Test
    public static void main(String[] args) {
        ArrayList<Integer> ints = new ArrayList<Integer>() {{
            add(1);
            add(2);
            add(3);
            add(4);
            add(5);
        }};
        ints.forEach(integer -> {
            if (integer < 2) {
                return;
            }
            System.out.println(integer);
        });
    }
}
