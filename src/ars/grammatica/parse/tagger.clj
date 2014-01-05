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
    ars.grammatica.parse.tagger
    (:use [ars.grammatica.parse.token]
          [ars.grammatica.parse.tokenizer]
          [ars.grammatica.morphology.analysis]
          [ars.grammatica.lexicon.latin-lexicon]))


(defn tag-tokens [token-seq]
  (if (empty? token-seq)
    nil
    (lazy-seq
      (cons
        (add-analyses (first token-seq) (fetch-analyses (:form (first token-seq))))
        (tag-tokens (rest token-seq))))))


(defn tag [input-type char-sequence]
    "Accepts an input-type designator (one of :latin, :greek, :greek-betacode) and a sequence of character data and
returns a lazy sequence of tokens, each of which is tagged with zero or more analyses."
    (if (empty? char-sequence)
      nil
      (let [token-seq (tokenize input-type char-sequence)]
        (lazy-seq
          (cons
            (add-analyses (first token-seq) (fetch-analyses (:form (first token-seq))))
            (tag-tokens (rest token-seq)))))))



