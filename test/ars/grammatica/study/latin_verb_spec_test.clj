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
  ars.grammatica.study.latin-verb-spec-test
  (:use [clojure.test]
        [ars.grammatica.study.latin-verb-spec]))

(defn try-permutations [vs how-many]
  (dotimes [i how-many]
    (let [permutation (permute-verb-spec vs)]
      (is (valid? permutation))
      (is (= 1 (count (verb-spec-diff vs permutation)))))))

(deftest valid-test
  (is (not (valid? (make-verb-spec "1st" "sg" "fut" "subj" "act"))))
  (is (not (valid? (make-verb-spec "1st" "sg" "futperf" "subj" "act"))))
  (is (not (valid? (make-verb-spec "1st" nil "pres" "subj" "act"))))
  (is (valid? (make-verb-spec "1st" "sg" "fut" "ind" "act"))))

(deftest rand-verb-spec-test
  (dotimes [i 10000]
    (is (valid? (rand-verb-spec)))))

(deftest permute-test
  (try-permutations (rand-verb-spec) 1000))

(deftest permute-subj-test
  (let [vs (make-verb-spec "1st" "sg" "pres" "subj" "act")]
    ;; run permute enough times to be satisfied that subj fut or subj future perfect is never created
    (try-permutations vs 1000)))


(deftest permute-future-test
  (let [vs (make-verb-spec "1st" "sg" "fut" "ind" "act")]
    ;; run permute enough times to be satisfied that subj fut or subj future perfect is never created
    (try-permutations vs 1000)))


(deftest permute-future-perfect-test
  (let [vs (make-verb-spec "1st" "sg" "futperf" "ind" "act")]
    ;; run permute enough times to be satisfied that subj fut or subj future perfect is never created
    (try-permutations vs 1000)))

