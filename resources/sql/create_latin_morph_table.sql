-- *********************************************************************************************
-- Copyright (C) 2012 Roger Grantham
-- 
-- All rights reserved.
-- 
-- This file is part of ArsGrammatica
-- 
-- ArsGrammatica is free software; you can redistribute it and/or modify it under
-- the terms of the GNU General Public License as published by the Free Software Foundation;
-- either version 3 of the License, or (at your option) any later version.
-- 
-- ArsGrammatica is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
-- without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
-- See the GNU General Public License for more details.
-- 
-- You should have received a copy of the GNU General Public License along with this program;
-- if not, see <http://www.gnu.org/licenses>.
-- 
-- Additional permission under GNU GPL version 3 section 7
-- 
-- If you modify ArsGrammatica, or any covered work, by linking or combining it with
-- clojure or clojure.contrib (or a modified version of that library),
-- containing parts covered by the terms of Eclipse Public License 1.1, the licensors of 
-- ArsGrammatica grant you additional permission to convey the resulting work. Corresponding 
-- Source for a non-source form of such a combination shall include the source code for the 
-- parts of clojure, clojure.contrib used as well as that of the covered work.
-- *********************************************************************************************
           
CREATE TABLE ARS.LATIN_MORPHOLOGY (
       form varchar(100) NOT NULL,
       plain_form varchar(100) NOT NULL,
       lemma varchar(100) NOT NULL,
       pos   varchar(6) NOT NULL
       	     CONSTRAINT MORPH_POS_CHECK
	          CHECK (pos IN ('noun', 'verb', 'adj', 'adv', 'prep', 'conj', 'exclam', 'pron')),
	   person varchar(3) 
	        CONSTRAINT MORPH_PERSON_CHECK
            CHECK (person in ('1st', '2nd', '3rd', '')),
       num varchar(2) CONSTRAINT MORPH_NUM_CHECK CHECK (num IN ('sg', 'pl', '')),  
       tense varchar(7) CONSTRAINT MORPH_TENSE_CHECK CHECK (tense in ('', 'imperf', 'perf', 'futperf', 'pres', 'plup', 'fut')),
       mood varchar(9) CONSTRAINT MORPH_MOOD_CHECK CHECK (mood in ('', 'ind', 'gerundive', 'inf', 'supine', 'imperat', 'subj')),
       voice varchar(4) CONSTRAINT MORPH_VOICE_CHECK CHECK (voice in ('', 'act', 'pass')),
       gender varchar(4) CONSTRAINT MORPH_GENDER_CHECK CHECK (gender in ('', 'masc', 'neut', 'fem')),
       gcase  varchar(3) CONSTRAINT MORPH_GCASE_CHECK CHECK (gcase in ('', 'dat', 'gen', 'voc', 'abl', 'nom', 'acc')),
       degree varchar(6) CONSTRAINT MORPH_DEGREE_CHECK  CHECK (degree in ('', 'comp', 'superl')));
       

CREATE INDEX lat_morph_form on ARS.LATIN_MORPHOLOGY (form);
CREATE INDEX lat_morph_plain_form on ARS.LATIN_MORPHOLOGY (plain_form);
CREATE INDEX lat_morph_lemma on ARS.LATIN_MORPHOLOGY (lemma);
CREATE INDEX lat_morph_pos on ARS.LATIN_MORPHOLOGY (pos);
CREATE INDEX lat_morph_person on ARS.LATIN_MORPHOLOGY (person);
CREATE INDEX lat_morph_num on ARS.LATIN_MORPHOLOGY (num);
CREATE INDEX lat_morph_tense on ARS.LATIN_MORPHOLOGY (tense);
CREATE INDEX lat_morph_mood on ARS.LATIN_MORPHOLOGY (mood);
CREATE INDEX lat_morph_voice on ARS.LATIN_MORPHOLOGY (voice);
CREATE INDEX lat_morph_gender on ARS.LATIN_MORPHOLOGY (gender);
CREATE INDEX lat_morph_gcase on ARS.LATIN_MORPHOLOGY (gcase);
CREATE INDEX lat_morph_degree on ARS.LATIN_MORPHOLOGY (degree);
