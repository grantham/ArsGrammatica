;;;; *********************************************************************************************
;;;; Copyright (C) ${today.year} Roger Grantham
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
    ars.grammatica.morphology.latin.generate-verbs-test
  (:use [ars.grammatica.morphology.latin.generate-verbs]
        [ars.grammatica.lexicon.entry]
        [clojure.test]))

(def amo (verb "amō" "amāre" "amāuī" "amātus" :conjugation-3 "love, like, be fond of"))
(def deleo (verb "dēleō" "dēlēre" "dēlēuī" "dēlētus" :conjugation-2 "blot out, destroy"))
(def duco (verb "dūcō" "dūcere" "dūxī" "ductus" :conjugation-3 "(imperative dūc), lead, conduct"))
(def audio (verb "audiō" "audīre" "audīuī" "audītus" :conjugation-4 "hear, listen to"))
(def capio (verb "capiō" "capere" "cēpī" "captus" :conjugation-5 "take, seize, capture"))
(def sequor (verb "sequor" "sequī" "secūtus sum" "" :conjugation-3 "follow" :deponent))

(deftest find-endings-test
  (is (not (nil? (find-endings :conjugation-1 :pres :ind :act))))
  (is (not (nil? (find-endings :conjugation-2 :pres :ind :act))))
  (is (not (nil? (find-endings :conjugation-3 :pres :ind :act))))
  (is (not (nil? (find-endings :conjugation-4 :pres :ind :act))))
  (is (not (nil? (find-endings :conjugation-5 :pres :ind :act)))))

(deftest stem-test
  (is (= "am" (stem "amō" "ō"))))

(deftest get-present-stem-test
  (is (= "am" (get-present-stem (verb "amō" "amāre" "amāuī" "amātus" :conjugation-3 "love, like, be fond of"))))
  (is (= "sequ" (get-present-stem (verb "sequor" "sequī" "secūtus sum" "" :conjugation-3 "follow" :deponent)))))

(defn assert-form [expected-form analysis]
  (is (= expected-form (:form analysis))))

(defn assert-forms [expected-forms entry conj tense mood voice]
  (doall
    (map
      assert-form
      expected-forms
      (analyses-from-endings entry (find-endings conj tense mood voice)))))

(deftest analyses-from-endings-test
  ;; present indicative active
  (assert-forms ["amō" "amās" "amat" "amāmus" "amātis" "amant"] amo :conjugation-1 :pres :ind :act)
  (assert-forms ["dēleō" "dēlēs" "dēlet" "dēlēmus" "dēlētis" "dēlent"] deleo :conjugation-2 :pres :ind :act)
  (assert-forms ["dūcō" "dūcis" "dūcit" "dūcimus" "dūcitis" "dūcunt"] duco :conjugation-3 :pres :ind :act)
  (assert-forms ["audiō" "audīs" "audit" "audīmus" "audītis" "audiunt"] audio :conjugation-4 :pres :ind :act)
  (assert-forms ["capiō" "capis" "capit" "capimus" "capitis" "capiunt"] capio :conjugation-5 :pres :ind :act)
  ;; present indicative passive
  ;; present subjunctive active
  (assert-forms ["amem" "amēs" "amet" "amēmus" "ametis" "ament"] amo :conjugation-1 :pres :subj :act)
  (assert-forms ["dēleam" "dēleās" "dēleat" "dēleāmus" "dēleātis" "dēleant"] deleo :conjugation-2 :pres :subj :act)
  (assert-forms ["dūcam" "dūcās" "dūcat" "dūcāmus" "dūcātis" "dūcant"] duco :conjugation-3 :pres :subj :act)
  (assert-forms ["audiam" "audiās" "audiat" "audiāmus" "audiātis" "audiant"] audio :conjugation-4 :pres :subj :act)
  (assert-forms ["capiam" "capiās" "capiat" "capiāmus" "capiātis" "capiant"] capio :conjugation-5 :pres :subj :act)
  )

