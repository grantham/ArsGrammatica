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
  (:use [ars.grammatica.lexicon.latin-lexicon-generator]
        [ars.grammatica.lexicon.entry]
        [ars.grammatica.morphology.analysis]))

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
                       (mk-endings :conjugation-1 :pres :ind :pas "ōr" "āris" "ātur" "āmur" "āminī" "antur" "āre" nil)
                       (mk-endings :conjugation-1 :pres :subj :act "em" "ēs" "et" "ēmus" "etis" "ent")
                       (mk-endings :conjugation-1 :pres :subj :pas "er" "ēris" "ētur" "ēmur" "ēminī" "entur" "ēre" nil)

                       (mk-endings :conjugation-1 :imperf :ind :act "ābam" "ābas" "ābat" "ābamus" "ābatis" "ābant")
                       (mk-endings :conjugation-1 :imperf :ind :pas "ābar" "ābāris" "ābātur" "ābāmur" "ābāminī" "ābantur" "ābāre" nil)
                       (mk-endings :conjugation-1 :imperf :subj :act "ārem" "ārēs" "āret" "ārēmus" "ārētis" "ārent")
                       (mk-endings :conjugation-1 :imperf :subj :pas "ārer" "ārēris" "ārētur" "ārēmur" "ārēminī" "ārentur" "arēre" nil)

                       (mk-endings :conjugation-1 :fut :ind :act "ābō" "ābis" "ābit" "ābīmus" "ābitits" "ābunt")
                       (mk-endings :conjugation-1 :fut :ind :pas "ābor" "āberis" "ābitur" "ābimur" "ābiminī" "ābuntur")

                       (mk-endings :conjugation-1 :perf :ind :act "āuī" "āuistī" "āuit" "āuimus" "āuistis" "āuērunt" nil "āuēre")
                       (mk-endings :conjugation-1 :perf :subj :act "āuerim" "āuerīs" "āuerit" "āuerīmus" "āuerītis" "āverint")
                       (mk-endings :conjugation-1 :perf :ind :pas "ātus sum" "ātus es" "ātus est" "ātī sumus" "ātī estis" "ātī sunt")
                       (mk-endings :conjugation-1 :perf :subj :pas "ātus sim" "ātus sīs" "ātus sit" "ātī sīmus" "ātī sītos" "ātī sint")

                       (mk-endings :conjugation-1 :plup :ind :act "āueram" "āuerās" "āuerat" "auerāmus" "āuertis" "āuerant")
                       (mk-endings :conjugation-1 :plup :subj :act "āuissem" "āuissēs" "auisset" "āuisset" "āuissēmus" "āuissētis" "āuissent")
                       (mk-endings :conjugation-1 :plup :ind :pas "ātus eram" "ātus erās" "ātus erat" "ātī erāmus" "ātī erātis" "ātī erant")
                       (mk-endings :conjugation-1 :plup :subj :pas "ātus essem" "ātus essēs" "ātus esset" "ātī essēmus" "ātī essētis" "ātī essent")

                       (mk-endings :conjugation-1 :futperf :ind :act "āuerō" "āuerīs" "āuerit" "āuerīmus" "āuerīmus" "āueritis" "āuerint")
                       (mk-endings :conjugation-1 :futperf :ind :pas "ātus erō" "ātus eris" "ātus erit" "ātī erimus" "ātī eritis" "ātī erunt")

                       (mk-endings :conjugation-1 :pres :imperat :act nil "ā" nil nil "āte" nil)
                       (mk-endings :conjugation-1 :pres :imperat :pas nil "āre" nil nil "āminī" nil)

                       (mk-endings :conjugation-1 :fut :imperat :act nil "ātō" "ātō" nil "ātōte" "antō")
                       (mk-endings :conjugation-1 :fut :imperat :pas nil "ātor" "ātor" nil nil "antantor")



                       ;; 2
                       (mk-endings :conjugation-2 :pres :ind :act "eō" "ēs" "et" "ēmus" "ētis" "ent")
                       (mk-endings :conjugation-2 :pres :subj :act "eam" "eās" "eat" "eāmus" "eātis" "eant")
                       ;; 3
                       (mk-endings :conjugation-3 :pres :ind :act "ō" "is" "it" "imus" "itis" "unt")
                       (mk-endings :conjugation-3 :pres :subj :act "am" "ās" "at" "āmus" "ātis" "ant")
                       ;; 4
                       (mk-endings :conjugation-4 :pres :ind :act "iō" "īs" "it" "īmus" "ītis" "iunt")
                       (mk-endings :conjugation-4 :pres :subj :act "iam" "iās" "iat" "iāmus" "iātis" "iant")
                       ;; 5
                       (mk-endings :conjugation-5 :pres :ind :act "iō" "is" "it" "imus" "itis" "iunt")
                       (mk-endings :conjugation-5 :pres :subj :act "iam" "iās" "iat" "iāmus" "iātis" "iant")
                       ])

(defn- find-endings [conjugation tense mood voice]
  (first (doall
      (filter
             (fn [x] (and
                 (= conjugation (:conjugation x))
                 (= tense (:tense x))
                 (= mood (:mood x))
                 (= voice (:voice x))))
             regular-endings))))



(defn generate-verb-analyses [verb-entry]

 )
