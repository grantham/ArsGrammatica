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
;;;; containing parts covered by the terms of Eclipse Public License, the licensors of
;;;; ArsGrammatica grant you additional permission to convey the resulting work. Corresponding
;;;; Source for a non-source form of such a combination shall include the source code for the
;;;; parts of clojure, clojure.contrib used as well as that of the covered work.
;;;; *********************************************************************************************
(ns  ^{:author "Roger Grantham"}
  ars.grammatica.morphology.analysis)

;;
;; Represents a morphological analysis of a word form
;;

(defrecord Analysis [form lemma pos person number tense mood voice gender gcase degree definition])

(defmethod print-method org.magnopere.ars.grammatica.analysis.Analysis [analysis writer]
  (.write writer (str "Analysis#<"
                   (reduce
                     #(if (nil? (first (second %2))) %1 (str %1 (if (empty? %1) "" ", ") (first %2) " " (second %2)))
                     ""
                     analysis)
                   (format ">"))))

(defn make-analysis [analysis-data]
  "Accepts a map of data found in the analysis and creates from it a new analysis"
  (merge (Analysis. nil nil nil nil nil nil nil nil nil nil nil nil) analysis-data))