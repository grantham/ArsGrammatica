;;;; *********************************************************************************************
;;;; Copyright (C) $today.year Roger Grantham
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
;;;;
;;;; The functions in this file provide access to Latin and Greek morphological and lexical data
;;;;
(ns  ^{:author "Roger Grantham"}
  org.magnopere.ars.grammatica.data.query
  (:use [clojure.core]
        [org.magnopere.ars.grammatica.analysis])
  (:require [clojure.java [jdbc :as jdbc]]))

(declare prepare-ro-db)

;; see http://db.apache.org/derby/docs/10.5/devguide/devguide-single.html#cdevdeploy15325
(defn prepare-ro-db []
  "When run as a read-only database, Derby requires a writeable temp location for large sorts,
  as well as a writeable output file or OutputStream for communicating errors. Returns a map of the
  connection properties which together define the JDBC connection URL"
  (System/setProperty "derby.storage.tempDirectory" (System/getProperty "java.io.tmpdir"))
  (System/setProperty "derby.stream.error.file" (.getAbsolutePath (java.io.File/createTempFile "derby-errors" ".log")))
  ;; classpath connection information
  {:classname "org.apache.derby.jdbc.EmbeddedDriver"
   :subprotocol "derby:classpath"
   :subname "db"
   :create false})

;;
;; This database contains all the morphological and lexical Latin and Greek information
;;
(def db (prepare-ro-db))

(defn fetch-analyses [form]
  "Accepts a word form (which has already been orthographically normalized and returns a list of zero or more analyses."
  (jdbc/with-connection db
    (jdbc/with-query-results rs [(str "select a.form, a.lemma, a.pos, a.person, a.number, a.tense, a.mood, a.voice, "
                                   "a.gender, a.gcase, a.degree, b.definition from ARS.morphology a "
                                   "natural join ARS.lexicon b where a.form = '" form "'")]
      (doall (map make-analysis rs)))))

