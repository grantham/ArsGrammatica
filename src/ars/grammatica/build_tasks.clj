;;;; *********************************************************************************************
;;;; Copyright (C) 2014 Roger Grantham
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
(ns ^{:author "Roger Grantham"}
  ars.grammatica.build-tasks
  (:use [clojure.tools.cli]
        [clojure.java.io :as io]
        [ars.grammatica.data.sql-writer]))

(defn run-ij [path-to-script]
  "start ij and executes the given sql script"
  (println "executing script " path-to-script)
  (.. Runtime getRuntime (exec (into-array String ["ij" path-to-script])))
  (println "Please ensure DB tmp directory does not exist before preparing embedded r/o DB"))


(defn -main [& args]
  (println "Ars Grammatica build tasks")
  (let [[options supplied-args banner]
        (clojure.tools.cli/cli args
                               ["-h" "--help"
                                "Print this help message."
                                :default false :flag true ]
                               ["-s" "--generate-sql"
                                "generates the SQL to populate the lexicon and morphology data. Supply a string path to where the SQL is to be written."
                                :default false :flag false]
                               ["-d" "--generate-db"
                                "loads the generated SQL into a new derby database to be pacakged with the app."
                                :default false :flag true])]
    
    (when (:help options)
      (println banner))
    (when (:generate-sql options)
      (create-latin-lexicon-sql (:generate-sql options)))
    (when (:generate-db options)
      (run-ij "./lexiconSetup.sql"))
    (println "generation tasks complete")))
