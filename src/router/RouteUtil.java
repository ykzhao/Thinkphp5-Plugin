package router;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.debugger.values.ArrayValue;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RouteUtil {

    private static final Pattern controllerFilePattern = Pattern.compile(".*/([\\w-/]+)/controller/([\\w-/]+).php$");

    public static Boolean isRouteFile(PsiFile file) {
        //规约: 在application 路径下,文件名为route或xxx route xxx的文件
        String name = file.getName();
        Project project = file.getProject();
        String replace = name.replace(project.getBasePath() + "application/", "").replace(".php", "");
        String route = replace.toLowerCase();
        if (route.contains("/") || !route.contains("route")) {
            return false;
        }
        return true;
    }

    public static Boolean isArrayValue(PsiElement element) {
//        if (element instanceof ) {
//            return true;
//        }
        return false;
    }

    public static ControllerFileMatchResult matchControllerFile(Project project, VirtualFile virtualFile) {
        String path = virtualFile.getPath();
        String projectPath = project.getBaseDir().getPath();

        if (path.startsWith(projectPath)) {
            path = path.substring(projectPath.length());
        }
        Matcher matcher = controllerFilePattern.matcher(path);
        if (matcher.matches()) {
            String prefix1 = matcher.group(1);
            String prefix2 = matcher.group(2);
            String prefix = prefix1 + "/" + prefix2;
            return new ControllerFileMatchResult(true, prefix);
        } else {
            return new ControllerFileMatchResult(false, "");
        }
    }

    public static class ControllerFileMatchResult {
        private boolean matches;

        private String keyPrefix;

        ControllerFileMatchResult(boolean matches, @NotNull String keyPrefix) {
            this.matches = matches;
            this.keyPrefix = keyPrefix;
        }

        public boolean matches() {
            return matches;
        }

        @NotNull
        public String getKeyPrefix() {
            return keyPrefix;
        }
    }
}