package com.pavel_kaminsky;

import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.apache.http.client.utils.URIBuilder;

import javax.swing.*;
import java.net.URISyntaxException;

public class J2TToolWindowFactory implements ToolWindowFactory {

    private JPanel content;
    private JComboBox typeComboBox;
    private JTextArea jsonTextArea;
    private JButton convertButton;
    private JTextPane outputTextPane;
    private JButton resetButton;
    private ToolWindow toolWindow;


    public J2TToolWindowFactory() {
        convertButton.addActionListener(e -> J2TToolWindowFactory.this.convert());
        resetButton.addActionListener(e -> J2TToolWindowFactory.this.reset());
    }

    private void reset() {
        jsonTextArea.setText("");
        outputTextPane.setText("");
    }

    public void createToolWindowContent(Project project, ToolWindow toolWindow) {
        this.toolWindow = toolWindow;
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(this.content, "", false);
        toolWindow.getContentManager().addContent(content);
    }

    public void convert() {
        String inputJSON = jsonTextArea.getText();
        String typedScheme = fetch(inputJSON);
        outputTextPane.setText(typedScheme);
    }

    public String formatTSURI(String input) throws URISyntaxException {
        URIBuilder ub = new URIBuilder("http://json2ts.com/Home/GetTypeScriptDefinition");
        ub.addParameter("code", input);
        ub.addParameter("ns", "someModule");
        ub.addParameter("root", "root");
        return ub.toString();
    }

    public String fetch(String input) {
        try {
            HttpRequestFactory requestFactory = new NetHttpTransport().createRequestFactory();
            HttpRequest request = requestFactory
                    .buildPostRequest(new GenericUrl(formatTSURI(input)), new EmptyContent());
            return request
                    .execute()
                    .parseAsString()
                    .replace("\"", "")
                    .replace("\\r\\n", "<br/>");
        } catch (Exception e) {
            return "Oh-Oh! Something went wrong";
        }
    }

}