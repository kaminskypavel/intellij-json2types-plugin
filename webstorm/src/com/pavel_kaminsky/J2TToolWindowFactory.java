package com.pavel_kaminsky;

import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.gson.*;
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
    private JButton prettifyJSONButton;
    private ToolWindow toolWindow;

    public void createToolWindowContent(Project project, ToolWindow toolWindow) {
        this.toolWindow = toolWindow;
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(this.content, "", false);
        toolWindow.getContentManager().addContent(content);
    }

    public J2TToolWindowFactory() {
        convertButton.addActionListener(e -> J2TToolWindowFactory.this.convert());
        resetButton.addActionListener(e -> J2TToolWindowFactory.this.reset());
        prettifyJSONButton.addActionListener(e -> J2TToolWindowFactory.this.prettifyJsonTextArea());
    }

    private void prettifyJsonTextArea() {
        String uglyJson = this.jsonTextArea.getText();
        String prettyJson;
        try {
            prettyJson = uglyJson.length() == 0 ? "" :
                    JSONUtils.prettifyJSON(uglyJson);
        } catch (JsonSyntaxException e) {
            prettyJson = "Invalid JSON";
        }
        this.jsonTextArea.setText(prettyJson);
    }

    private void reset() {
        jsonTextArea.setText("");
        outputTextPane.setText("");
    }

    private void convert() {
        String inputJSON = jsonTextArea.getText();
        String jsonType = typeComboBox.getModel().getSelectedItem().toString().toLowerCase();
        String typedScheme = RemoteServer.fetch(inputJSON, jsonType);
        outputTextPane.setText(typedScheme);
    }
}