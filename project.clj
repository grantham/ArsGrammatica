;;;; *********************************************************************************************
;;;; Copyright (C) 2012 Roger Grantham
;;;;
;;;; All rights reserved.
;;;;
;;;; This file is part of ArsGrammatica
;;;;
;;;; ArsGrammatica is free software; you can redistribute it and/or
;;;; modify it under the terms of the GNU General Public License as
;;;; published by the Free Software Foundation; either version 3 of
;;;; the License, or (at your option) any later version.
;;;;
;;;; ArsGrammatica is distributed in the hope that it will be useful,
;;;; but WITHOUT ANY WARRANTY; without even the implied warranty of
;;;; MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
;;;; General Public License for more details.
;;;;
;;;; You should have received a copy of the GNU General Public License
;;;; along with this program; if not, see
;;;; <http://www.gnu.org/licenses>.
;;;;
;;;; Additional permission under GNU GPL version 3 section 7
;;;;
;;;; If you modify ArsGrammatica, or any covered work, by linking or
;;;; combining it with clojure or clojure.contrib (or a modified
;;;; version of that library), containing parts covered by the terms
;;;; of Eclipse Public License, the licensors of ArsGrammatica grant
;;;; you additional permission to convey the resulting work.
;;;; Corresponding Source for a non-source form of such a combination
;;;; shall include the source code for the parts of clojure,
;;;; clojure.contrib, used as well as that of the covered work.
;;;; ***********************************************************************
(defproject ArsGrammatica "0.0.1-SNAPSHOT"
  :description "Ars Grammatica provides Latin morphological and syntactical tutoring and practice."
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [org.clojure/java.jdbc "0.1.1"]
                 [jline/jline "1.0"]
                 [org.apache.derby/derby "10.8.2.2"]
                 [org.clojure/tools.cli "0.2.1"]]
  :aot [ars.grammatica.core]
  :main org.magnopere.ars.grammatica.core)

;; TODO: add a build set to create the database if it doesn't exist:
;; 0. Check whether the resources/lexicon database exists, if not:
;; 1. decompress the *.sql.tar.gz scripts under resources/sql
;; 2. invoke $ ij lexiconSetup.sql
;; 3. delete resources/lexicon/tmp if it exists

;; TODO: when the db build step is completed, add a build hook to this script to invoke it before jar or uberjar