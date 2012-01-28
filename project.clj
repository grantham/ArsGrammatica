;;;; *********************************************************************************************
;;;; Copyright (C) 2012 Roger Grantham
;;;;
;;;; All rights reserved.
;;;;
;;;; This file is part of ArsGrammatica
;;;;
;;;; ArsGrammatica is free software; you can redistribute it and/or modify it under
;;;; the terms of the GNU General Public License as published by the Free Software Foundation;
;;;; either version 3 of the License, or (at your option) any later version.
;;;;
;;;; ArsGrammatica is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
;;;; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
;;;; See the GNU General Public License for more details.
;;;;
;;;; You should have received a copy of the GNU General Public License along with this program;
;;;; if not, see <http://www.gnu.org/licenses>.
;;;;
;;;; Additional permission under GNU GPL version 3 section 7
;;;;
;;;; If you modify ArsGrammatica, or any covered work, by linking or combining it with
;;;; clojure or clojure.contrib (or a modified version of that library),
;;;; containing parts covered by the terms of Mozilla Public License 1.1,
;;;; or lexicon.sql and language.sql (part of Ars Grammatica) containing parts covered by the terms
;;;; of Mozilla Public License 1.1, or morphology.sql (part of Ars Grammatica) containing parts covered
;;;; by the terms of the Creative Commons ShareAlike 3.0 License, the licensors of ArsGrammatica grant
;;;; you additional permission to convey the resulting work. Corresponding Source for a non-source form
;;;; of such a combination shall include the source code for the parts of clojure, clojure.contrib,
;;;; lexicon.sql, morphology.sql, and language.sql used as well as that of the covered work.
;;;; *********************************************************************************************
(defproject ArsGrammatica "0.0.1-SNAPSHOT"
  :description "Ars Grammatica provides Latin morphological and syntactical tutoring and practice."
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [jline/jline "1.0"]
                 [org.apache.derby/derby "10.8.2.2"]
                 [org.clojure/tools.cli "0.2.1"]]
  :main org.magnopere.ars.grammatica.core)