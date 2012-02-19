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
(ns ^{:author "Roger Grantham"}
  ars.grammatica.parse.tokenizer
  (:use [clojure.core]
        [ars.grammatica.text]
        [ars.grammatica.parse.token])
  (:require [clojure.string :as s]))

(declare normalize-latin)



(def normalizer {:latin normalize-latin
                 :greek #(%)
                 :greek-betacode #(%) })

(defn tokenize [input-type char-sequence]
  "Accepts an input-type designator (one of :latin, :greek, :greek-betacode) and a sequence of character data
    and returns a lazy sequence of word form tokens, e.g. (tokenize \"μῆνιν ἄειδε θεὰ\")"
  (if (empty? char-sequence)
    nil
    (let [words (re-seq #"\p{L}+|\p{Punct}+|\p{Digit}+" char-sequence)]
      (letfn [(next-token [w]
                (if (empty? w)
                  nil
                  (lazy-seq (cons (make-token ((input-type normalizer) (first w)))
                              (next-token (rest w))))))]
        (if (empty? words)
          nil
          (lazy-seq
            (cons
              (make-token ((input-type normalizer) (first words)))
              (next-token (rest words)))))))))
