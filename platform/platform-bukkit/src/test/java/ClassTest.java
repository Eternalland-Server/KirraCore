import lombok.val;
import net.sakuragame.eternal.kirracore.bukkit.KirraCoreBukkit;
import net.sakuragame.eternal.kirracore.bukkit.util.ClassUtil;
import org.junit.Test;

public class ClassTest {

    @Test
    public static void main(String[] args) {
        val clazz = ClassUtil.getClassesInPackage(KirraCoreBukkit.getInstance(), "net.sakuragame.eternal.kirracore.bukkit");
        clazz.forEach(clazzName -> {
            System.out.println(clazzName);
        });
    }
}
