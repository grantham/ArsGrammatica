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
  ars.grammatica.lexicon.entry)

;; (noun lemma [genitive|--] [:masculine|:feminine|:neuter] [1|2|3|4|5|:greek|:indeclinable] "definition" [:plural]?)
;;   (noun "aciēs" "aciēī" :feminine 5 "edge; line of battle")
;;   (noun "Etrūscī" "Etrūscōrum" :masculine :plural 2 "the Etruscans, the people of Etruria.")
(defrecord noun-entry [lemma genitive gender declension definition number])

;; (adjective m f n gen [2|3|:indefinite] "definition")
;;   (adjective "albus" "alba" "album" "albī" 2 "white")
;;   (adjective "aliqui" "aliqua" "aliquod" "alicūius" :indefinite "some, any")
(defrecord adjective-entry [m f n gen declension definition])

;; (verb first-singular [infinitive|--] [first-perfect|--] [perfect participle|--] [1|2|3|4|5|:irregular] definition [:semideponent|:deponent]?)
(defrecord verb-entry [first-present inf first-perfect participle conjugation deponent-type definition])

;; (adverb lemma def)
(defrecord adverb-entry [lemma comparative superlative def])

;; (conjunction lemma def)
(defrecord conjunction-entry [lemma def])

;; (preposition lemma [:ablative|:accusative] def)
(defrecord preposition-entry [lemma governing-case definition])


(defrecord exclam-entry [lemma definition])

;; pronoun type is relative|interrogative
(defrecord pronoun-entry [m f n pronoun-type defition])


(def num-to-declension {:indeclinable "indeclinable"
                        :greek "greek"
                        1 "1st"
                        2 "2nd"
                        3 "3rd"
                        4 "4th"
                        5 "5th"})

(defn- declension-from-number [declension]
  (get num-to-declension declension))

(def num-to-conjugation {:irregular "irregular"
                         1 "1st"
                         2 "2nd"
                         3 "3rd"
                         4 "4th"
                         5 "5th"})

(defn- conjugation-from-number [conjugation]
  (get num-to-conjugation conjugation))


(defn noun [lemma genitive gender declension definition & number]
  (noun-entry.
    lemma
    genitive
    gender
    (declension-from-number declension)
    definition
    number))

;; TODO: irregular comparatives
(defn adjective [m f n gen declension definition]
  (adjective-entry.
    m
    f
    n
    gen
    (declension-from-number declension)
    definition))

(defn verb [first-present inf first-perfect participle conjugation definition & deponent-type]
  (verb-entry.
    first-present
    inf
    first-perfect
    participle
    (conjugation-from-number conjugation)
    deponent-type
    definition))

;; (adverb lemma def)
(defn adverb
  ([lemma def]
    (adverb-entry. lemma "" "" def))
  ([lemma comparative superlative def]
    (adverb-entry. lemma comparative superlative def)))

(defn conjunction [lemma definition]
  (conjunction-entry.
    lemma
    definition))

(defn preposition [lemma governing-case definition]
  (preposition-entry.
    lemma
    governing-case
    definition))
