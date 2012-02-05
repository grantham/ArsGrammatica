/*
 * ;;;; *********************************************************************************************
 * ;;;; Copyright (C) 2012 Roger Grantham
 * ;;;;
 * ;;;; All rights reserved.
 * ;;;;
 * ;;;; This file is part of ArsGrammatica
 * ;;;;
 * ;;;; ArsGrammatica is free software; you can redistribute it and/or modify it under
 * ;;;; the terms of the GNU General Public License as published by the Free Software Foundation;
 * ;;;; either version 3 of the License, or (at your option) any later version.
 * ;;;;
 * ;;;; ArsGrammatica is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * ;;;; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * ;;;; See the GNU General Public License for more details.
 * ;;;;
 * ;;;; You should have received a copy of the GNU General Public License along with this program;
 * ;;;; if not, see <http://www.gnu.org/licenses>.
 * ;;;;
 * ;;;; Additional permission under GNU GPL version 3 section 7
 * ;;;;
 * ;;;; If you modify ArsGrammatica, or any covered work, by linking or combining it with
 * ;;;; clojure or clojure.contrib (or a modified version of that library),
 * ;;;; containing parts covered by the terms of Mozilla Public License 1.1,
 * ;;;; or lexicon.sql and language.sql (part of Ars Grammatica) containing parts covered by the terms
 * ;;;; of Mozilla Public License 1.1, or morphology.sql (part of Ars Grammatica) containing parts covered
 * ;;;; by the terms of the Creative Commons ShareAlike 3.0 License, the licensors of ArsGrammatica grant
 * ;;;; you additional permission to convey the resulting work. Corresponding Source for a non-source form
 * ;;;; of such a combination shall include the source code for the parts of clojure, clojure.contrib,
 * ;;;; lexicon.sql, morphology.sql, and language.sql used as well as that of the covered work.
 * ;;;; *********************************************************************************************
 */

package xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Roger Grantham
 * @since 1/22/12
 */
public class MorphParser extends DefaultHandler {
    

    private String          pcData;
    private Analysis        analysis;
    private BufferedWriter  bufferedWriter;
    private int             language = 2; // 2 = greek; 3 = latin

    public MorphParser(BufferedWriter bufferedWriter) {
        this.bufferedWriter = bufferedWriter;
    }

    public void setLanguage(int language) {
        this.language = language;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if ("analysis".equals(qName)){
            analysis = new Analysis();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if ("analysis".equals(qName)){
            writeAnalysis(analysis);
            analysis = null;
        } else if ("analyses".equals(qName)){
            // no op
        } else {
            analysis.set(qName, pcData);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        pcData = new String(ch, start, length);
    }
    

    private void writeCreateStmnt() throws Exception {
        final String table = "CREATE TABLE ARS.morphology (\n" +
                "form varchar(100),\n" +
                "lemma varchar(100),\n" +
                "pos varchar(100),\n" +
                "person varchar(100),\n" +
                "number varchar(100),\n" +
                "tense varchar(100),\n" +
                "mood varchar(100),\n" +
                "voice varchar(100),\n" +
                "gender varchar(100),\n" +
                "gcase varchar(100),\n" +
                "degree varchar(100),\n" +
                "dialect varchar(100),\n" +
                "feature varchar(100),\n" +
                "language_id int);\n"+
                "CREATE INDEX a on ARS.morphology(form);\n" +
                "CREATE INDEX b on ARS.morphology(lemma);\n" +
                "CREATE INDEX c on ARS.morphology(pos);\n" +
                "CREATE INDEX d on ARS.morphology(person);\n" +
                "CREATE INDEX e on ARS.morphology(number);\n" +
                "CREATE INDEX f on ARS.morphology(tense);\n" +
                "CREATE INDEX g on ARS.morphology(mood);\n" +
                "CREATE INDEX h on ARS.morphology(voice);\n" +
                "CREATE INDEX i on ARS.morphology(gender);\n" +
                "CREATE INDEX j on ARS.morphology(gcase);\n" +
                "CREATE INDEX k on ARS.morphology(degree);\n" +
                "CREATE INDEX l on ARS.morphology(dialect);\n" +
                "CREATE INDEX m on ARS.morphology(feature);\n" +
                "CREATE INDEX n on ARS.morphology(language_id);\n" +
                "set schema ARS;\n";
        bufferedWriter.write(table);
    }

    private void writeAnalysis(Analysis analysis) {
     //(form, lemma, pos, person, number, tense, mood, voice, gender, case, degree, dialect, feature, language)
        final String insert = String.format("INSERT INTO morphology " +
          "VALUES (\'%s\', \'%s\', \'%s\', \'%s\', \'%s\', \'%s\', \'%s\', \'%s\', \'%s\', \'%s\', \'%s\', \'%s\', \'%s\', %d);%n",
                analysis.getForm(), analysis.getLemma(), analysis.getPos(), analysis.getPerson(), analysis.getNumber(),
                analysis.getTense(), analysis.getMood(), analysis.getVoice(), analysis.getGender(), analysis.getCase(),
                analysis.getDegree(), analysis.getDialect(), analysis.getFeature(), language);
        try{
            bufferedWriter.write(insert);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 5) throw new IllegalArgumentException("args: input1 lang input2 lang output.sql");
        BufferedWriter bw = null;
        InputStream is1 = null;
        InputStream is2 = null;
        try {
            final String input1 = args[0];
            final String input1lang = args[1];
            final String input2 = args[2];
            final String input2lang = args[3];
            final String outputSQL = args[4];
            bw = new BufferedWriter(new FileWriter(new File(outputSQL)));
            is1 = new FileInputStream(input1);
            final MorphParser handler = new MorphParser(bw);
            handler.writeCreateStmnt();
            // first file
            handler.setLanguage(Integer.parseInt(input1lang));
            final SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            parser.parse(is1, handler);
            bw.flush();
            // second file
            handler.setLanguage(Integer.parseInt(input2lang));
            is2 = new FileInputStream(input2);
            parser.parse(is2, handler);
            bw.flush();
        } finally {
            if (is1 != null) is1.close();
            if (is2 != null) is2.close();
            if (bw != null) bw.close();
        }
    }


    private class Analysis {
        private final Map<String,String> fields = new HashMap<String, String>();
        
        private Analysis() {
            for (String field: Arrays.asList("form", "lemma", "pos", "person", "number", "tense", "mood", "voice", "gender", "case", "degree", "dialect", "feature")){
                fields.put(field, null);    
            }   
        }


        public String rectifyOrthography(String word){
            String rectified = word.toLowerCase().replaceAll("[#]\\d*?$", "");
            if (3 == language){
                rectified =  rectified.replaceAll("v", "u").replaceAll("j", "i");
            }
            return rectified;
        }

        public void set(String fieldName, String value){
            if (!fields.containsKey(fieldName)){
                throw new IllegalStateException("Invalid field name: " + fieldName);
            }                                            
            fields.put(fieldName, value);
        }
        
        private String get(String fieldName){
            final String val = fields.get(fieldName);
            return val == null ? "" :  val.replaceAll("'", "''");
        }
        
        public String getForm() {
            return rectifyOrthography(get("form"));
        }

        public String getLemma() {
            return rectifyOrthography(get("lemma"));
        }

        public String getPos() {
            return get("pos");
        }

        public String getPerson() {
            return get("person");
        }

        public String getNumber() {
            return get("number");
        }

        public String getTense() {
            return get("tense");
        }

        public String getMood() {
            return get("mood");
        }

        public String getVoice() {
            return get("voice");
        }

        public String getGender() {
            return get("gender");
        }

        public String getCase() {
            return get("case");
        }

        public String getDegree() {
            return get("degree");
        }

        public String getDialect() {
            return get("dialect");
        }

        public String getFeature() {
            return get("feature");
        }
    }

    
    
}
