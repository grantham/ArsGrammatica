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
-- containing parts covered by the terms of Mozilla Public License 1.1,
-- or lexicon.sql and language.sql (part of Ars Grammatica) containing parts covered by the terms
-- of Mozilla Public License 1.1, or morphology.sql (part of Ars Grammatica) containing parts covered
-- by the terms of the Creative Commons ShareAlike 3.0 License, the licensors of ArsGrammatica grant
-- you additional permission to convey the resulting work. Corresponding Source for a non-source form
-- of such a combination shall include the source code for the parts of clojure, clojure.contrib,
-- lexicon.sql, morphology.sql, and language.sql used as well as that of the covered work.
-- *********************************************************************************************
--
-- This script is intended to be used with ij to initialize and populate a new lexicon database under resources/
-- This script presupposes that the *.tar.gz files under resources/sql have been decompressed.
--
-- After this script has been run, it must be prepared for read-only use
--  (see http://db.apache.org/derby/docs/10.5/devguide/devguide-single.html#cdevdeploy15325)
-- which chiefly just requires deleting the resources/lexicon/tmp directory (if it exists).
--
-- Invocation:
-- $ ij lexiconSetup.sql
--

connect 'jdbc:derby:resources/lexicon;create=true';
run 'resources/sql/language.sql';
run 'resources/sql/lexicon.sql';
run 'resources/sql/morphology.sql';
run 'resources/sql/latin_verb_metadata.sql';
exit;