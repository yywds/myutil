package com.example.aidanci.word;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;

public class Parse {

    static Netword word;
    static public Netword parseXML(InputStream is)throws Exception{
        word = new Netword();
        Pronunciation pronunciation = new Pronunciation();
        Mean mean = new Mean();
        Sent sent = new Sent();
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        XmlPullParser parser = factory.newPullParser();
        parser.setInput(is,"UTF-8");
        int type = parser.getEventType();
        while (type != XmlPullParser.END_DOCUMENT){
            String nodeName = parser.getName();
            switch (type){
                case XmlPullParser.START_TAG:
                    if("key".equals(nodeName)){
                        word.setKey(parser.nextText());
                    }
                    else if("ps".equals(nodeName)){
                        pronunciation = new Pronunciation();
                        pronunciation.setPs(parser.nextText());
                    }
                    else if("pron".equals(nodeName)){
                        pronunciation.setPron(parser.nextText());
                        word.setPronunciations(pronunciation);
                    }
                    else if ("pos".equals(nodeName)){
                        mean = new Mean();
                        mean.setPos(parser.nextText());
                    }
                    else if ("acceptation".equals(nodeName)){
                        mean.setAcceptation(parser.nextText());
                        word.setMeans(mean);
                    }
                    else if("sent".equals(nodeName)){
                        sent = new Sent();
                    }
                    else if("orig".equals(nodeName)){
                        sent.setOrig(parser.nextText());
                    }
                    else if("trans".equals(nodeName)){
                        sent.setTrans(parser.nextText());
                        word.setSents(sent);
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if("dict".equals(nodeName))
                        break;
                default:
                    break;
            }
            type = parser.next();
        }
        return word;
    }
}