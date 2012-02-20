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
    ars.grammatica.data.sql-writer
  (:import [java.io File BufferedWriter FileWriter])
  (:use [ars.grammatica.lexicon.latin-lexicon-generator]
        [ars.grammatica.text])
  (:require [clojure.string :as s]))

(declare write-adjectives write-adverbs write-conjunctions write-prepositions write-verbs)

(def create-table-path "sql/create_lat_lex_table.sql")



(def declensions {:indeclinable "indeclinable"
                        :greek "greek"
                        :declension-1 "1st"
                        :declension-2 "2nd"
                        :declension-3 "3rd"
                        :declension-4 "4th"
                        :declension-5 "5th"})

(defn- declension-to-string [declension]
  (get declensions declension))

(def conjugations {:irregular "irregular"
                         :conjugation-1 "1st"
                         :conjugation-2 "2nd"
                         :conjugation-3 "3rd"
                         :conjugation-4 "4th"
                         :conjugation-5 "5th"})

(defn- conjugation-to-string [conjugation]
  (get conjugations conjugation))


(defn create-latin-lexicon-sql [^String path]
  "Writes the sql require to create the latin lexicon database table to a file described by path."
  (let [out-file (File. path)]
    (when (not (.exists out-file))
      (.createNewFile out-file))
    (with-open [bw (BufferedWriter. (FileWriter. out-file))]
      (.write bw (slurp-resource create-table-path))
      (.newLine bw)
      (.flush bw)
      (write-adjectives bw)
      (write-adverbs bw)
      (write-conjunctions bw)
      (write-prepositions bw)
      (write-nouns bw)
      (write-verbs bw))))

(defn q
  ([s]
    "Quotes the given string"
    (str "'" s "'"))
  ([key rec]
    "Quotes the given value of the given record identified by the key."
    (if (nil? (key rec))
      "''"
      (str "'" (key rec) "'"))))

(defn quote-keyword [k]
  (if (nil? k) "''"
    (q (s/replace k ":" ""))))

(defn write-entries [bw insert-sql make-entries-fn write-entry-fn]
  (.write bw insert-sql)
  (let [started (ref false)]
  (doseq [entry (make-entries-fn)]
    (if @started
        (.write bw ", ")
        (dosync (ref-set started true)))
    (.newLine bw)
    (write-entry-fn bw entry))
    (.write bw ";")
    (.newLine bw)
    (.flush bw)))

(defn write-adjective [bw adj]
  (.write
      bw
      (str "(" (q :m adj) ", 'adj', "  (q (remove-macrons (:m adj))) ", " (q :gen adj) ", " (q (declension-to-string (:declension adj)))
        ", " (q :m adj) ", " (q :f adj) ", " (q :n adj) ", " (q :definition adj) ")" )))

(defn write-adjectives [bw]
  ;; TODO: cull non-positive degrees
  (.newLine bw)
  (write-entries bw
    (str "INSERT INTO ARS.LATIN_LEXICON (lemma, pos, plain_lemma, genitive, declension, "
         "masculine_form, feminine_form, neuter_form, definition) VALUES")
    make-adjectives
    write-adjective))

;; adverb-entry [lemma comparative superlative def]
(defn write-adverb [bw adv]
  (.write bw (str "(" (q :lemma adv) ", 'adv', " (q (remove-macrons (:lemma adv))) ", "
                  (q :comparative adv)", "  (q :superlative adv)", "  (q :definition adv) ")")))

(defn write-adverbs [bw]
  (.newLine bw)
  (write-entries bw
    "INSERT INTO ARS.LATIN_LEXICON (lemma, pos, plain_lemma, comparative_form, superlative_form, definition) VALUES"
    make-adverbs
    write-adverb))

(defn write-conjunction [bw conj]
  (.write bw (str "(" (q :lemma conj) ", 'conj', " (q (remove-macrons (:lemma conj))) ", " (q :definition conj) ")")))

;; conjunction [lemma definition]
(defn write-conjunctions [bw]
  (.newLine bw)
  (write-entries bw
    "INSERT INTO ARS.LATIN_LEXICON (lemma, pos, plain_lemma, definition) VALUES"
    make-conjunctions
    write-conjunction))

(defn write-noun [bw noun]
  (.write bw (str "(" (q :lemma noun) ", 'noun', "  (q (remove-macrons (:lemma noun))) ", " (q :gen noun) ", "
               (q (declension-to-string (:declension noun))) ", " (quote-keyword (:number noun)) ", " (q :definition noun) ")")))

;; noun [lemma genitive gender declension definition & number]
(defn write-nouns [bw]
  (.newLine bw)
  (write-entries bw
    "INSERT INTO ARS.LATIN_LEXICON (lemma, pos, plain_lemma, genitive, declension, number, definition) VALUES"
    make-nouns
    write-noun))

(defn write-preposition [bw prep]
  (.write bw (str "(" (q :lemma prep) ", 'prep', " (q (remove-macrons (:lemma prep))) ", "
               (quote-keyword (:governing-case prep)) ", " (q :definition prep) ")")))

;; preposition [lemma governing-case definition]
(defn write-prepositions [bw]
  (.newLine bw)
  (write-entries bw
    "INSERT INTO ARS.LATIN_LEXICON (lemma, pos, plain_lemma, governing_case, definition) VALUES"
    make-prepositions
    write-preposition))

(defn write-verb [bw verb]
  (.write bw
    (str "(" (q :first-present verb) ", 'verb', " (q (remove-macrons (:first-present verb))) ", "
      (q :first-present verb) ", " (q :inf verb) ", " (q :first-perfect verb) ", " (q :participle verb) ", "
      (q (conjugation-to-string (:conjugation verb))) ", "
      (quote-keyword (:deponent-type verb))
      ", " (q :definition verb) ")")))

;; verb [first-present inf first-perfect participle conjugation definition & deponent-type]
(defn write-verbs [bw]
  (.newLine bw)
  (write-entries bw
    (str "INSERT INTO ARS.LATIN_LEXICON (lemma, pos, plain_lemma, first_present, infinitive, first_perfect, "
          "participle, conjugation, deponent_type, definition) VALUES")
    make-verbs
    write-verb))



