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
  org.magnopere.ars.grammatica.tokenizer
  (:use [clojure.core])
  (:require [clojure.string :as s]))

;;
;; Represents a tokenized word form with slots for the later analysis and lexicon entry
;;  http://docs.oracle.com/javase/6/docs/api/java/text/Normalizer.html
(defrecord token [word-form analysis entry])

(defmethod print-method org.magnopere.ars.grammatica.tokenizer.token [t w]
  (print-method [ (:word-form t) (:analysis-entry t) (:entry t)] w))

(defn rectify-orthography [word]
  "Normalizes Latin orthography to simplest sensible system."
  (if (empty? word)
    nil
    (s/replace (s/replace (s/replace word \j \i ) \v \u) \J \I)))

(defn tokenize [char-sequence]
  "Accepts a sequence of character data and returns a lazy sequence of word form tokens, e.g.
  (tokenize \"μῆνιν ἄειδε θεὰ\")"
  (if (empty? char-sequence)
    nil
    (let [words (re-seq #"\p{L}+|\p{Punct}+|\p{Digit}+" char-sequence)]
      (letfn [(next-token [w]
                (if (empty? w)
                  nil
                  (lazy-seq (cons (token. (rectify-orthography (first w)) nil nil)
                              (next-token (rest w))))))]
        (if (empty? words)
          nil
          (lazy-seq
            (cons
              (token. (rectify-orthography (first words)) nil nil)
              (next-token (rest words)))))))))
