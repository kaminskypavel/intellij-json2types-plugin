package com.pavel_kaminsky;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;

import javax.swing.*;

public class J2TToolWindowFactory implements ToolWindowFactory {

    private JPanel content;
    private JComboBox typeComboBox;
    private JTextArea jsonTextArea;
    private JButton convertButton;
    private JTextPane outputTextPane;
    private ToolWindow toolWindow;


    public J2TToolWindowFactory() {
        convertButton.addActionListener(e -> J2TToolWindowFactory.this.convert());
    }

    public void createToolWindowContent(Project project, ToolWindow toolWindow) {
        this.toolWindow = toolWindow;
        this.convert();
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(this.content, "", false);
        toolWindow.getContentManager().addContent(content);
    }

    public void convert() {
        System.out.println(typeComboBox.getSelectedItem());
        System.out.println(jsonTextArea.getText());
        System.out.println(outputTextPane.getText());
    }

    public String fetch(String url, String input) {
        try {
            HttpRequestFactory requestFactory = new NetHttpTransport().createRequestFactory();
            HttpRequest request = requestFactory.buildGetRequest(new GenericUrl(url));
            String rawResponse = request.execute().parseAsString();
            return rawResponse;
        } catch (Exception e) {
            return "Oh-Oh! Something went wrong";
        }
    }

}