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
CREATE TABLE ARS.LATIN_LEXICON (
       lemma varchar(100) NOT NULL,
       pos   varchar(6) NOT NULL
       	     CONSTRAINT POS_CHECK
	          CHECK (pos IN ('noun', 'verb', 'adj', 'adv', 'prep', 'conj', 'exclam', 'pron')),
       plain_lemma varchar(100) NOT NULL,
       -- verb fields NOT NULL when pos = 'verb'
       first_present varchar(100),
       infinitive varchar(100),
       first_perfect varchar(100),
       participle varchar(100),
       conjugation varchar(9)
       		   CONSTRAINT CONJUGATION_CHECK
		        CHECK (conjugation IN ('1st', '2nd', '3rd', '4th', '5th', 'irregular', '')),
       deponent_type varchar(13)
       		     CONSTRAINT DEPONENT_CHECK
		          CHECK (deponent_type IN ('deponent', 'semi-deponent', '')),
       -- noun fields NOT NULL when pos = 'noun'
       genitive varchar(100),
       gender varchar(1) CONSTRAINT GENDER_CHECK CHECK (gender IN ('m', 'f', 'n', '')),
       declension varchar(12)
       		  CONSTRAINT DECLENSION_CHECK
		      CHECK (declension IN ('1st', '2nd', '3rd', '4th', '5th', 'indeclinable', 'greek', '')),
       num varchar(2) CONSTRAINT NUM_CHECK CHECK (num IN ('sg', 'pl', '')),
       -- adjective fields NOT NULL when pos = 'adj'
       masculine_form varchar(100),
       feminine_form varchar(100),
       neuter_form varchar(100),
       -- adverb fields NOT NULL when pos = 'adv'
       comparative_form varchar(100),
       superlative_form varchar(100),
       -- N/A conjunction fields
       -- preposition fields  NOT NULL when pos = 'prep'
       governing_case varchar(10)
       		      CONSTRAINT CASE_CHECK
		          CHECK (governing_case IN ('accusative', 'ablative', '')),
       -- TODO pronoun fields
       definition varchar(500) NOT NULL);

CREATE INDEX lat_lex_lemma on ARS.LATIN_LEXICON (lemma);
CREATE INDEX lat_lex_pos on ARS.LATIN_LEXICON (pos);
CREATE INDEX lat_lex_plain_lemma on ARS.LATIN_LEXICON (plain_lemma);
CREATE INDEX lat_lex_conj on ARS.LATIN_LEXICON (conjugation);
CREATE INDEX lat_lex_decl on ARS.LATIN_LEXICON (declension);
CREATE INDEX lat_lex_dep_type on ARS.LATIN_LEXICON (deponent_type);