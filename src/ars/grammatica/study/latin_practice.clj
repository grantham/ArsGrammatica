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
(ns  ars.grammatica.study.latin-practice
  (:use [ars.grammatica.study.latin-verb-spec]
        [ars.grammatica.morphology.analysis]
        [ars.grammatica.lexicon.latin-lexicon]))


;conjugations 1 - 5
;regular, deponent, semi-deponent, irregular

(def verb-exemplars ["amo" "moneo" "rego" "audio" "capio"
                     "hortor" "vereor" "sequor" "partior"
                     "sum" "possum" "prosum" "volo" "nolo" "malo" "fero" "eo" "fio"])

(defn get-analyses [lemma vs]
  (fetch-verb-analyses 3 lemma (:person vs) (:number vs) (:tense vs) (:mood vs) (:voice vs)))

(defn verb-mutation-exercise []
  (let [lemma (rand-nth verb-exemplars )
        vs-from (rand-verb-spec)
        vs-to (permute-verb-spec vs-from)
        diff (verb-spec-diff vs-from vs-to)]
    (println lemma)
    (println vs-from)
    (println (get-analyses lemma vs-from))
    (println vs-to)
    (println (get-analyses lemma vs-to))
    (println diff)
    ))





