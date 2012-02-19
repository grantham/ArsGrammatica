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
    ars.grammatica.text
  (:require [clojure.string :as s]))

(def vowels {"ā" "a" "ē" "e" "ī" "i" "ō" "o" "ū" "u" "Ā" "A" "Ē" "E" "Ī" "I" "Ō" "O" "Ū" "U"})
(def consonants {"j" "i" "v" "u" "J" "I" "U" "V"})

(defn slurp-resource [path]
  "Given a FULL classpath resource, returns a string of that resource. Do not start the path with a '/'"
  (with-open [cp-resource (.getResourceAsStream (clojure.lang.RT/baseLoader) path)]
    (slurp  cp-resource)))

(defn- reduce-replace [s m]
  (reduce #(s/replace %1 (first %2) (first (rest %2))) s m))

(defn remove-macrons [s]
  "Replaces any vowel letter with a macron with the corresponding letter without a macron"
  (reduce-replace s vowels))

;; see http://docs.oracle.com/javase/6/docs/api/java/text/Normalizer.html
(defn normalize-latin [word]
  "Normalizes Latin orthography to simplest sensible system."
  (reduce-replace word consonants ))

