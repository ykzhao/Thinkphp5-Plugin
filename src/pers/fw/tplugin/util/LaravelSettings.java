package pers.fw.tplugin.util;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.project.Project;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.Nullable;
import pers.fw.tplugin.view.dict.TemplatePath;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
@State(
    name = "LaravelPluginSettings",
    storages = {
        @Storage("laravel-plugin.xml")
    }
)
public class LaravelSettings implements PersistentStateComponent<LaravelSettings> {

    public boolean pluginEnabled = false;
    public boolean useAutoPopup = false;
    public String routerNamespace;
    public String mainLanguage;

    @Nullable
    public List<TemplatePath> templatePaths = new ArrayList<>();

    public boolean dismissEnableNotification = false;

    public static LaravelSettings getInstance(Project project) {
        return ServiceManager.getService(project, LaravelSettings.class);
    }

    public String getMainLanguage() {
        return !StringUtils.isBlank(mainLanguage) ? mainLanguage : "en";
    }

    @Nullable
    @Override
    public LaravelSettings getState() {
        return this;
    }

    @Override
    public void loadState(LaravelSettings settings) {
        XmlSerializerUtil.copyBean(settings, this);
    }

}
