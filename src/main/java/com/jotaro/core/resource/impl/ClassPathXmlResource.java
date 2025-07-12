package com.jotaro.core.resource.impl;

import com.jotaro.core.resource.Resource;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.net.URL;
import java.util.Iterator;

/**
 * @author caihengJotaro
 * @date 2025/7/11
 * @description xml资源解析器
 */
public class ClassPathXmlResource implements Resource {

    private Document document;

    private Element rootElement;

    private Iterator<Element> iterator;

    public ClassPathXmlResource(String fileName) {
        SAXReader reader = new SAXReader();
        URL url = this.getClass().getClassLoader().getResource(fileName);
        try{
            this.document = reader.read(url);
            this.rootElement = document.getRootElement();
            this.iterator = this.rootElement.elementIterator();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public boolean hasNext() {
        return this.iterator.hasNext();
    }

    @Override
    public Object next() {
        return this.iterator.next();
    }
}
