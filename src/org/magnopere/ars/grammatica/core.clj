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
(ns ^{:author "Roger Grantham"}
      org.magnopere.ars.grammatica.core
  (:use [clojure.core] [clojure.tools.cli]
        [org.magnopere.ars.grammatica.tagger])
  (:gen-class))

(declare tag-input)

(def prompt "ArsGrammatica> ")
(def console-reader (jline.ConsoleReader. ))

(defn -main [& args]
  (println "Ars Grammatica")
  (let [[options supplied-args banner]
        (clojure.tools.cli/cli args
          ["-h" "--help" "Print this help message." :default false :flag true ]
          ["-nw" "--noWindow" "Run Ars Grammatica in the console. By default Ars Grammatica runs in a graphical UI" :default false :flag true]
          ["-t" "--tag" "Accepts input from stdin and writes a tagged token stream to stout."  :default false :flag true ]
          )]
    (when (:help options)
      (println banner))
    (when (:tag options)
      (tag-input))
    (println options)))


(defn tag-input []
  (let [input (.readLine console-reader prompt)]
    (println (tag :latin input))
  (if (= ":quit" input)
      0
      (recur))))


