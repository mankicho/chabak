package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    @RequestMapping(value = "/")
    public String home() {
        return "index";
    }

    @RequestMapping(value = "/get")
    public void process(HttpServletRequest req) {
        String str = req.getParameter("address");

        ScriptEngineManager manager = new ScriptEngineManager();
        List<ScriptEngineFactory> engineFactories = manager.getEngineFactories();

        for (ScriptEngineFactory factory : engineFactories) {
            System.out.println("engine name: " + factory.getEngineName());
            System.out.println("engine version: " + factory.getEngineVersion());

            String extensions = String.join(", ", factory.getExtensions());
            System.out.println("extensions: " + extensions);

            System.out.println("language name: " + factory.getLanguageName());
            System.out.println("language version: " + factory.getLanguageVersion());

            String mimeTypes = factory.getMimeTypes().stream()
                    .collect(Collectors.joining(", "));
            System.out.println("mimeTypes: " + mimeTypes);

            String shortNames = factory.getNames().stream()
                    .collect(Collectors.joining(", "));
            System.out.println("shortNames :" + shortNames);

            String[] params = {
                    ScriptEngine.NAME, ScriptEngine.ENGINE,
                    ScriptEngine.ENGINE_VERSION, ScriptEngine.LANGUAGE,
                    ScriptEngine.LANGUAGE_VERSION
            };

            for (String param : params) {
                System.out.printf("parameter '%s': %s\n", param, factory.getParameter(param));
            }
            System.out.println("---------------");
        }

    }
}
