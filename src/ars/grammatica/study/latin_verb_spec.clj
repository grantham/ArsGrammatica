;;;; *********************************************************************************************
;;;; Copyright (C) $today.year Roger Grantham
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
(ns  org.magnopere.ars.grammatica.study.latin-verb-spec
  (:use [clojure.set]))

(def verb-attributes { :person ["1st" "2nd" "3rd"]
                       :number ["sg" "pl"]
                       :tense ["imperf" "perf" "futperf" "pres" "plup" "fut"]
                       :mood  ["ind"  "subj"] ;; ignore these moods for now: "gerundive" "inf" "supine" "imperat"
                       :voice ["act" "pass"]})

(defrecord verb-spec [person number tense mood voice])

(defn make-verb-spec [person number tense mood voice]
  (verb-spec. person number tense mood voice))

(defn- verb-spec-values [vs]
  (into #{} (map (fn [e] e) vs)))

(defn verb-spec-diff [vs1 vs2]
  "Furnishes the differences between the two verb-specs as a list of map entries."
  (difference (verb-spec-values vs1) (verb-spec-values vs2)))

(defn verb-spec-eql [vs1 vs2]
  (empty? (verb-spec-diff vs1 vs2)))

(defn valid? [vs]
  "Returns true when all fields are non-null and does not indicate subjunctive mood with a future tense."
  (not
    (or
      (some #(nil? (first (rest %))) vs)
      (and
        (or (= "fut" (:tense vs)) (= "futperf" (:tense vs)))
        (= "subj" (:mood vs)))))                            )

(defn- make-filter [ak verb-spec]
  ;; remove the current value of the verb-spec's attribute-key from the list of attribute values from which we randomly pick
  ;; if key is :tense and verb-spec :mood is subjunctive, do not allow fut or futperf
  ;; if key is :mood and tense is fut or futperf, do not allow subj
  (fn [att-value]
    (not
      (or
        (= (ak verb-spec) att-value)
        (and (or (= "fut" att-value) (= "futperf" att-value)) (= "subj" (:mood verb-spec)))
        (and (= "subj" att-value) (or (= "fut" (:tense verb-spec)) (= "futperf" (:tense verb-spec))))))))

(defn- rand-attribute-value
  ([attribute-key]
    (rand-nth (attribute-key verb-attributes)))
  ([attribute-key verb-spec]
    (rand-nth (filter (make-filter attribute-key verb-spec) (attribute-key verb-attributes)))))

(defn- rand-attribute-key []
  "Randomly chooses an attribute key of the verb spec"
  (rand-nth [:person :number :tense :mood :voice]))

(defn rand-verb-spec []
  "Creates a verb-spec with random (but valid) attribute values"
  (let [vs (verb-spec.
    (rand-attribute-value :person)
    (rand-attribute-value :number)
    (rand-attribute-value :tense)
    (rand-attribute-value :mood)
    (rand-attribute-value :voice))]
    (if (valid? vs)
      vs
      (recur))))

(defn permute-verb-spec [verb-spec]
  "Changes one attribute value of the verb spec randomly; results in a new valid verb-spec."
  (let [ak (rand-attribute-key)]
    (merge verb-spec {ak (rand-attribute-value ak verb-spec)})))


