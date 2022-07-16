import net.sakuragame.eternal.kirracore.bukkit.compat.CompatHandler;
import net.sakuragame.eternal.kirracore.bukkit.compat.mythicmobs.CompatMythicMobsHandler;
import org.junit.Test;

public class ClassTest {

    @Test
    public static void main(String[] args) {
        System.out.println(CompatHandler.class.isAssignableFrom(CompatMythicMobsHandler.class));
        System.out.println(CompatMythicMobsHandler.class.isAssignableFrom(CompatHandler.class));
    }
}
