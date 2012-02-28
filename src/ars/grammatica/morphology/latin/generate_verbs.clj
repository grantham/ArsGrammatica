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
    ars.grammatica.morphology.latin.generate-verbs
  (:use [ars.grammatica.text]
        [ars.grammatica.lexicon.latin-lexicon-generator]
        [ars.grammatica.lexicon.entry]
        [ars.grammatica.morphology.analysis])
  (:require [clojure.string :as s]))

(def personal-endings {:s1 "1st" :s2 "2nd" :s3 "3rd" :p1 "1st" :p2 "2nd" :p3 "3rd" :s2-alt "2nd" :p3-alt "3rd"})
(def number {:s1 "sg" :s2 "sg" :s3 "sg" :p1 "pl" :p2 "pl" :p3 "pl" :s2-alt "sg" :p3-alt "pl"})

;; tense -  (:imperf :perf :futperf :pres :plup :fut)
;; mood -  (:ind :gerundive :inf :supine :imperat :subj)
;; voice -  (:act :pass)
;; conjugation - (:conjugation-1 .. :conjugation-5, :irregular)
(defrecord endings [conjugation tense mood voice s1 s2 s3 p1 p2 p3 s2-alt p3-alt])

(defn mk-endings
  ([conjugation tense mood voice s1 s2 s3 p1 p2 p3]
    (endings. conjugation tense mood voice s1 s2 s3 p1 p2 p3 nil nil))
   ([conjugation tense mood voice s1 s2 s3 p1 p2 p3 s2-alt p3-alt]
    (endings. conjugation tense mood voice s1 s2 s3 p1 p2 p3 s2-alt p3-alt)))

(def regular-endings [
                       ;; 1
                       (mk-endings :conjugation-1 :pres :ind :act "ō" "ās" "at" "āmus" "ātis" "ant")
                       (mk-endings :conjugation-1 :pres :ind :pass "or" "āris" "ātur" "āmur" "āminī" "antur" "āre" nil)
                       (mk-endings :conjugation-1 :pres :subj :act "em" "ēs" "et" "ēmus" "etis" "ent")
                       (mk-endings :conjugation-1 :pres :subj :pass "er" "ēris" "ētur" "ēmur" "ēminī" "entur" "ēre" nil)

                       (mk-endings :conjugation-1 :imperf :ind :act "ābam" "ābas" "ābat" "ābamus" "ābatis" "ābant")
                       (mk-endings :conjugation-1 :imperf :ind :pass "ābar" "ābāris" "ābātur" "ābāmur" "ābāminī" "ābantur" "ābāre" nil)
                       (mk-endings :conjugation-1 :imperf :subj :act "ārem" "ārēs" "āret" "ārēmus" "ārētis" "ārent")
                       (mk-endings :conjugation-1 :imperf :subj :pass "ārer" "ārēris" "ārētur" "ārēmur" "ārēminī" "ārentur" "arēre" nil)

                       (mk-endings :conjugation-1 :fut :ind :act "ābō" "ābis" "ābit" "ābīmus" "ābitits" "ābunt")
                       (mk-endings :conjugation-1 :fut :ind :pass "ābor" "āberis" "ābitur" "ābimur" "ābiminī" "ābuntur")

                       (mk-endings :conjugation-1 :perf :ind :act "ī" "istī" "it" "imus" "istis" "ērunt" nil "ēre")
                       (mk-endings :conjugation-1 :perf :subj :act "erim" "erīs" "erit" "erīmus" "erītis" "erint")
                       (mk-endings :conjugation-1 :perf :ind :pass "us sum" "us es" "us est" "ī sumus" "ī estis" "ī sunt")
                       (mk-endings :conjugation-1 :perf :subj :pass "us sim" "us sīs" "us sit" "ī sīmus" "ī sītis" "ī sint")

                       (mk-endings :conjugation-1 :plup :ind :act "eram" "erās" "erat" "erāmus" "erātis" "erant")
                       (mk-endings :conjugation-1 :plup :subj :act "issem" "issēs" "isset" "issēmus" "issētis" "issent")
                       (mk-endings :conjugation-1 :plup :ind :pass "us eram" "us erās" "us erat" "ī erāmus" "ī erātis" "ī erant")
                       (mk-endings :conjugation-1 :plup :subj :pass "us essem" "us essēs" "us esset" "ī essēmus" "ī essētis" "ī essent")

                       (mk-endings :conjugation-1 :futperf :ind :act "erō" "eris" "erit" "erimus"  "eritis" "erint")
                       (mk-endings :conjugation-1 :futperf :ind :pass "us erō" "us eris" "us erit" "ī erimus" "ī eritis" "ī erunt")

                       (mk-endings :conjugation-1 :pres :imperat :act nil "ā" nil nil "āte" nil)

                       ;; 2
                       (mk-endings :conjugation-2 :pres :ind :act "eō" "ēs" "et" "ēmus" "ētis" "ent")
                       (mk-endings :conjugation-2 :pres :ind :pass "eor" "ēris" "ētur" "ēmur" "ēminī" "entur" "ēre" nil)
                       (mk-endings :conjugation-2 :pres :subj :act "eam" "eās" "eat" "eāmus" "eātis" "eant")
                       (mk-endings :conjugation-2 :pres :subj :pass "ear" "eāris" "eātur" "eāmur" "eāminī" "eantur" "eāre" nil)

                       (mk-endings :conjugation-2 :imperf :ind :act "ēbam" "ēbās" "ēbat" "ēbāmus" "ābātis" "ēbant")
                       (mk-endings :conjugation-2 :imperf :subj :act "ērem" "ērēs" "ēret" "ērēmus" "ērētis" "ērent")
                       (mk-endings :conjugation-2 :imperf :ind :pass "ēbar" "ēbāris" "ēbātur" "ēbāmur" "ēbāminī" "ēbantur" "ēbāre" nil)
                       (mk-endings :conjugation-2 :imperf :subj :pass "ērer" "ērēris" "ērētur" "ērēmur" "ērēmini" "ērentur" "ērēre" nil)

                       (mk-endings :conjugation-2 :fut :ind :act "ēbō" "ēbis" "ēbit" "ēbimus" "ēbitis" "ēbunt")
                       (mk-endings :conjugation-2 :fut :ind :pass "ēbor" "ēberis" "ēbitur" "ēbimur" "ēbiminī" "ēbuntur" "ēbere" nil)

                       (mk-endings :conjugation-2 :perf :ind :act "ī" "istī" "it" "imus" "istis" "ērunt" nil "ēre")
                       (mk-endings :conjugation-2 :perf :subj :act "erim" "erīs" "erit" "erīmus" "erītis" "erint")
                       (mk-endings :conjugation-2 :perf :ind :pass "us sum" "us es" "us est" "ī sumus" "ī estis" "ī sunt")
                       (mk-endings :conjugation-2 :perf :subj :pass "us sim" "us sīs" "us sit" "ī sīmus" "ī sītis" "ī sint")

                       (mk-endings :conjugation-2 :plup :ind :act  "eram" "erās" "erat" "erāmus" "erātis" "erant")
                       (mk-endings :conjugation-2 :plup :subj :act "issem" "issēs" "isset" "issēmus" "issētis" "issent")
                       (mk-endings :conjugation-2 :plup :ind :pass "us eram" "us erās" "us erat" "ī erāmus" "ī erātis" "ī erant")
                       (mk-endings :conjugation-2 :plup :subj :pass "us essem" "us essēs" "us esset" "ī essēmus" "ī essētis" "ī essent")

                       (mk-endings :conjugation-2 :futperf :ind :act  "erō" "eris" "erit" "erimus"  "eritis" "erint")
                       (mk-endings :conjugation-2 :futperf :ind :pass "us erō" "us eris" "us erit" "ī erimus" "ī eritis" "ī erunt")

                       (mk-endings :conjugation-2 :pres :imperat :act nil "ē" nil nil "ēte" nil)

                       ;; 3
                       (mk-endings :conjugation-3 :pres :ind :act "ō" "is" "it" "imus" "itis" "unt")
                       (mk-endings :conjugation-3 :pres :subj :act "am" "ās" "at" "āmus" "ātis" "ant")
                       (mk-endings :conjugation-3 :pres :ind :pas "or" "eris" "itur" "imur" "iminī" "untur" "ere" nil)
                       (mk-endings :conjugation-3 :pres :subj :pas "ar" "āris" "ātur" "āmur" "āminī" "antur" "āre" nil)

                       (mk-endings :conjugation-3 :imperf :ind :act "ēbam" "ēbās" "ābat" "ēbāmus" "ābātis" "ēbant")
                       (mk-endings :conjugation-3 :imperf :subj :act "erem" "erēs" "eret" "erēmus" "erētis" "erent")
                       (mk-endings :conjugation-3 :imperf :ind :pass "ēbar" "ēbāris" "ēbātur" "ēbāmur" "ēbāminī" "ēbantur" "ēbāre" nil)
                       (mk-endings :conjugation-3 :imperf :subj :pass "erer" "erēris" "erētur" "erēmur" "erēminī" "erentur" "erēre" nil)

                       (mk-endings :conjugation-3 :fut :ind :act "am" "ēs" "et" "ēmus" "ētis" "ent")
                       (mk-endings :conjugation-3 :fut :ind :pass "ar" "ēris" "ētur" "ēmur" "ēminī" "entur" "ēre" nil)

                       (mk-endings :conjugation-3 :perf :ind :act "ī" "istī" "it" "imus" "istis" "ērunt" nil "ēre")
                       (mk-endings :conjugation-3 :perf :subj :act "erim" "erīs" "erit" "erīmus" "erītis" "erint")
                       (mk-endings :conjugation-3 :perf :ind :pass "us sum" "us es" "us est" "ī sumus" "ī estis" "ī sunt")
                       (mk-endings :conjugation-3 :perf :subj :pass "us sim" "us sīs" "us sit" "ī sīmus" "ī sītis" "ī sint")

                       (mk-endings :conjugation-3 :plup :ind :act "eram" "erās" "erat" "erāmus" "erātis" "erant")
                       (mk-endings :conjugation-3 :plup :subj :act "issem" "issēs" "isset" "issēmus" "issētis" "issent")
                       (mk-endings :conjugation-3 :plup :ind :pass "us eram" "us erās" "us erat" "ī erāmus" "ī erātis" "ī erant")
                       (mk-endings :conjugation-3 :plup :subj :pass "us essem" "us essēs" "us esset" "ī essēmus" "ī essētis" "ī essent")

                       (mk-endings :conjugation-3 :futperf :ind :act "erō" "eris" "erit" "erimus"  "eritis" "erint")
                       (mk-endings :conjugation-3 :futperf :ind :pass "us erō" "us eris" "us erit" "ī erimus" "ī eritis" "ī erunt")

                       (mk-endings :conjugation-3 :pres :imperat :act nil "e" nil nil "ite" nil)

                       ;; 4
                       (mk-endings :conjugation-4 :pres :ind :act "iō" "īs" "it" "īmus" "ītis" "iunt")
                       (mk-endings :conjugation-4 :pres :subj :act "iam" "iās" "iat" "iāmus" "iātis" "iant")
                       (mk-endings :conjugation-4 :pres :ind :pas "ior" "īris" "ītur" "īmur" "īminī" "iumtur" "īre" nil)
                       (mk-endings :conjugation-4 :pres :subj :pas "iar" "iāris" "iātur" "iāmur" "iāminī" "iantur" "iāre" nil)

                       (mk-endings :conjugation-4 :imperf :ind :act "iēbam" "iēbās" "iēbat" "iēbāmus" "iēbātis" "iēbant")
                       (mk-endings :conjugation-4 :imperf :subj :act "īrem" "īrēs" "īret" "īrēmus" "īrētis" "īrent")
                       (mk-endings :conjugation-4 :imperf :ind :pass "iēbar" "iēbāris" "iēbātur" "iēbāmur" "iēbāminī" "iēbantur" "iēbāre" nil)
                       (mk-endings :conjugation-4 :imperf :subj :pass "īrer" "īrēris" "īrētur" "īrēmur" "īrēminī" "īrentur" "īrēre" nil)

                       (mk-endings :conjugation-4 :fut :ind :act "iam" "iēs" "iet" "iēmus" "iētis" "ient")
                       (mk-endings :conjugation-4 :fut :ind :pass "iar" "iēris" "iētur" "iēmur" "iēminī" "ientur" "iēre" nil)

                       (mk-endings :conjugation-4 :perf :ind :act "ī" "istī" "it" "imus" "istis" "ērunt" nil "ēre")
                       (mk-endings :conjugation-4 :perf :subj :act "erim" "erīs" "erit" "erīmus" "erītis" "erint")
                       (mk-endings :conjugation-4 :perf :ind :pass "us sum" "us es" "us est" "ī sumus" "ī estis" "ī sunt")
                       (mk-endings :conjugation-4 :perf :subj :pass "us sim" "us sīs" "us sit" "ī sīmus" "ī sītis" "ī sint")

                       (mk-endings :conjugation-4 :plup :ind :act "eram" "erās" "erat" "erāmus" "erātis" "erant")
                       (mk-endings :conjugation-4 :plup :subj :act "issem" "issēs" "isset" "issēmus" "issētis" "issent")
                       (mk-endings :conjugation-4 :plup :ind :pass "us eram" "us erās" "us erat" "ī erāmus" "ī erātis" "ī erant")
                       (mk-endings :conjugation-4 :plup :subj :pass "us essem" "us essēs" "us esset" "ī essēmus" "ī essētis" "ī essent")

                       (mk-endings :conjugation-4 :futperf :ind :act "erō" "eris" "erit" "erimus"  "eritis" "erint")
                       (mk-endings :conjugation-4 :futperf :ind :pass "us erō" "us eris" "us erit" "ī erimus" "ī eritis" "ī erunt")

                       (mk-endings :conjugation-4 :pres :imperat :act nil "ī" nil nil "īte" nil)

                       ;; 5
                       (mk-endings :conjugation-5 :pres :ind :act "iō" "is" "it" "imus" "itis" "iunt")
                       (mk-endings :conjugation-5 :pres :subj :act "iam" "iās" "iat" "iāmus" "iātis" "iant")
                       (mk-endings :conjugation-5 :pres :ind :pas  "ior" "eris" "itur" "imur" "iminī" "iuntur" "ere" nil)
                       (mk-endings :conjugation-5 :pres :subj :pas "iar" "iāris" "iātur" "iāmur" "iāminī" "iantur" "iāre" nil)

                       (mk-endings :conjugation-5 :imperf :ind :act "iēbam" "iēbās" "iēbat" "iēbāmus" "iēbātis" "iēbant")
                       (mk-endings :conjugation-5 :imperf :subj :act "erem" "erēs" "eret" "erēmus" "erētis" "erent")
                       (mk-endings :conjugation-5 :imperf :ind :pass "iēbar" "iēbāris" "iēbātur" "iēbāmur" "iēbāminī" "iēbantur" "iēbāre" nil)
                       (mk-endings :conjugation-5 :imperf :subj :pass "erer" "erēris" "erētur" "erēmur" "erēminī" "erentur" "erēre" nil)

                       (mk-endings :conjugation-5 :fut :ind :act "iam" "iēs" "iet" "iēmus" "iētis" "ient")
                       (mk-endings :conjugation-5 :fut :ind :pass "iar" "iēris" "iētur" "iēmur" "iēminī" "ientur" "iēre" nil)

                       (mk-endings :conjugation-5 :perf :ind :act "ī" "istī" "it" "imus" "istis" "ērunt" nil "ēre")
                       (mk-endings :conjugation-5 :perf :subj :act "erim" "erīs" "erit" "erīmus" "erītis" "erint")
                       (mk-endings :conjugation-5 :perf :ind :pass "us sum" "us es" "us est" "ī sumus" "ī estis" "ī sunt")
                       (mk-endings :conjugation-5 :perf :subj :pass "us sim" "us sīs" "us sit" "ī sīmus" "ī sītis" "ī sint")

                       (mk-endings :conjugation-5 :plup :ind :act "eram" "erās" "erat" "erāmus" "erātis" "erant")
                       (mk-endings :conjugation-5 :plup :subj :act "issem" "issēs" "isset" "issēmus" "issētis" "issent")
                       (mk-endings :conjugation-5 :plup :ind :pass "us eram" "us erās" "us erat" "ī erāmus" "ī erātis" "ī erant")
                       (mk-endings :conjugation-5 :plup :subj :pass "us essem" "us essēs" "us esset" "ī essēmus" "ī essētis" "ī essent")

                       (mk-endings :conjugation-5 :futperf :ind :act "erō" "eris" "erit" "erimus"  "eritis" "erint")
                       (mk-endings :conjugation-5 :futperf :ind :pass "us erō" "us eris" "us erit" "ī erimus" "ī eritis" "ī erunt")

                       (mk-endings :conjugation-5 :pres :imperat :act nil "e" nil nil "ite" nil)
                       ])

(def deponent-endings [
                        ])

(def semi-deponent-endings [])

(defn find-endings [conjugation tense mood voice]
  (first (doall
      (filter
             (fn [x] (and
                 (= conjugation (:conjugation x))
                 (= tense (:tense x))
                 (= mood (:mood x))
                 (= voice (:voice x))))
             regular-endings))))

(defn stem [form ending]
  "Returns form with the ending removed. Returns form when does not end with supplied ending. Returns nil when form is nil."
  (s/replace form (re-pattern (str ending "$")) ""))

(defn get-present-stem [verb-entry]
  (cond
    (or (= :conjugation-4 (:conjugation verb-entry))(= :conjugation-5 (:conjugation verb-entry)))
      (stem (:first-present verb-entry) "(?:iō)|(?:ior)")
    (= :conjugation-2 (:conjugation verb-entry))
      (stem (:first-present verb-entry) "(?:eō)|(?:eor)")
    :else (stem (:first-present verb-entry) "(?:ō)|(?:or)")))

(defn get-perfect-stem [verb-entry]
  (stem (:first-perfect verb-entry) "ī"))

(defn get-participle-stem [verb-entry]
  (stem (:participle verb-entry) "us"))

(defn stem-for-endings [verb-entry verb-endings]
  "Given a lexicon entry for a verb and the targeted endings instance, furnishes the right stem to which the endings
  can be applied to conjugate the verb."
  (let [tense (:tense verb-endings)
        voice (:voice verb-endings)]
    (cond
      (and
        (or (= :perf tense ) (= :plup tense)(= :futperf tense))
        (= :pass voice))
          (get-participle-stem verb-entry)
      (or (= :perf tense) (= :plup tense) (= :futperf tense))
          (get-perfect-stem verb-entry)
      :else (get-present-stem verb-entry))))

(defn matches-endings? [endings match-spec]
  "True if the endings instance matches the values in the match-spec (a map of endings fields).
  Keys: [conjugation tense mood voice s1 s2 s3 p1 p2 p3 s2-alt p3-alt]"
  (reduce
    #(and %1 (= ((first %2) endings) (first (rest %2))))
    true
    match-spec))

(defn make-irregular-imperative [verb-entry verb-endings]
  (cond
    (= "dīcō" (:first-present verb-entry))
    "dīc"
    (= "dūcō" (:first-present verb-entry))
    "dūc"
    :else nil))

(defn make-form [personal-ending-id verb-entry verb-endings]
  (cond
    (and
      (matches-endings? verb-endings {:tense :pres :mood :imperat :voice :act})
      (or (= "dīcō" (:first-present verb-entry)) (= "dūcō" (:first-present verb-entry)))
      (= personal-ending-id :s2))
    (make-irregular-imperative verb-entry verb-endings)
    :else (str (stem-for-endings verb-entry verb-endings) (personal-ending-id verb-endings))))

(defn analysis-from-endings [personal-ending-id verb-entry verb-endings]
  "Creates an analysis with the designated personal-ending-id (one of [:s1-:s3, :p1-:p3, :s2-alt, :p3-alt]) from the
  given entry and endings instances."
  (if (nil? (personal-ending-id verb-endings))
    nil
    (let [form (make-form personal-ending-id verb-entry verb-endings)]
      (make-analysis {
                       :form form
                       :plain-form (remove-macrons form)
                       :lemma (:first-present verb-entry)
                       :pos "verb"
                       :person (name (personal-ending-id personal-endings))
                       :number (name (personal-ending-id number))
                       :tense (name (:tense verb-endings))
                       :mood (name (:mood verb-endings))
                       :voice (name (:voice verb-endings))}))))

(defn analyses-from-endings [verb-entry endings]
  (filter (complement nil?)
    (list
      (analysis-from-endings :s1 verb-entry endings)
      (analysis-from-endings :s2 verb-entry endings)
      (analysis-from-endings :s3 verb-entry endings)
      (analysis-from-endings :p1 verb-entry endings)
      (analysis-from-endings :p2 verb-entry endings)
      (analysis-from-endings :p3 verb-entry endings)
      (analysis-from-endings :s2-alt verb-entry endings)
      (analysis-from-endings :p3-alt verb-entry endings))))

(defn generate-regular-analyses [verb-entry]
  (flatten
    (map
      #(analyses-from-endings verb-entry %)
      (filter #(= (:conjugation verb-entry) (:conjugation %)) regular-endings))))

(defn generate-verb-analyses []
  "Uses the core latin lexicon verb entries: for each verb entry, and analysis is created for each possible
  form of that verb."
  (map
    #(cond
        (= :irregular (:conjugation %))
        nil
        (= :deponent (:deponent-type %))
        nil
        (= :semi-deponent (:deponent-type %))
        nil
        :else (generate-regular-analyses %))
    (make-verbs)))



