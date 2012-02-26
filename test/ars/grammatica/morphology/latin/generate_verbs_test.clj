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
  ;;
  ;; PRESENT SYSTEM
  ;;
  (assert-forms ["amō" "amās" "amat" "amāmus" "amātis" "amant"] amo :conjugation-1 :pres :ind :act)
  (assert-forms ["dēleō" "dēlēs" "dēlet" "dēlēmus" "dēlētis" "dēlent"] deleo :conjugation-2 :pres :ind :act)
  (assert-forms ["dūcō" "dūcis" "dūcit" "dūcimus" "dūcitis" "dūcunt"] duco :conjugation-3 :pres :ind :act)
  (assert-forms ["audiō" "audīs" "audit" "audīmus" "audītis" "audiunt"] audio :conjugation-4 :pres :ind :act)
  (assert-forms ["capiō" "capis" "capit" "capimus" "capitis" "capiunt"] capio :conjugation-5 :pres :ind :act)

  (assert-forms ["amem" "amēs" "amet" "amēmus" "ametis" "ament"] amo :conjugation-1 :pres :subj :act)
  (assert-forms ["dēleam" "dēleās" "dēleat" "dēleāmus" "dēleātis" "dēleant"] deleo :conjugation-2 :pres :subj :act)
  (assert-forms ["dūcam" "dūcās" "dūcat" "dūcāmus" "dūcātis" "dūcant"] duco :conjugation-3 :pres :subj :act)
  (assert-forms ["audiam" "audiās" "audiat" "audiāmus" "audiātis" "audiant"] audio :conjugation-4 :pres :subj :act)
  (assert-forms ["capiam" "capiās" "capiat" "capiāmus" "capiātis" "capiant"] capio :conjugation-5 :pres :subj :act)

  (assert-forms ["amor" "amāris" "amātur" "amāmur" "amāminī" "amantur" "amāre"] amo :conjugation-1 :pres :ind :pass)
  (assert-forms ["dēleor" "dēlēris" "dēlētur" "dēlēmur" "dēlēminī" "dēlentur" "dēlēre" nil] deleo :conjugation-2 :pres :ind :pass)
  (assert-forms ["dūcor" "dūceris" "dūciur" "dūcimur" "dūcīminī" "dūcuntur" "dūcere" nil] duco :conjugation-3 :pres :ind :pass)
  (assert-forms ["audior" "audīris" "audītur" "audīmur" "audīminī" "audiuntur" "audīre" nil] audio :conjugation-4 :pres :ind :pass)
  (assert-forms ["capior" "caperis" "capitur" "capiminī" "capiuntur" "capere" nil] capio :conjugation-5 :pres :ind :pass)

  (assert-forms ["amer" "amēris" "amētur" "amēmur" "amēminī" "amentur" "amēre" nil] amo :conjugation-1 :pres :subj :pass)
  (assert-forms ["dēlear" "dēleāris" "dēleātur" "dēleāmur" "dēleāminī" "dēleantur" "dēleāre" nil] deleo :conjugation-2 :pres :subj :pass)
  (assert-forms ["dūcar" "dūcáris" "dūcātur" "dūcāmur" "dūcāminī" "dūcantur" "dūcāre" nil] duco :conjugation-3 :pres :subj :pass)
  (assert-forms ["audiar" "audiāris" "audiātur" "audiāmur" "audiāminī" "audiāntur" "audiāre" nil] audio :conjugation-4 :pres :subj :pass)
  (assert-forms ["capiar" "capiāris" "capiātur" "capiāmur" "capiāminī" "capiāntur" "capiāre" nil] capio :conjugation-5 :pres :subj :pass)
  ;;
  ;; IMPERFECT SYSTEM
  ;;
  (assert-forms ["amābam" "amābas" "amābat" "amābamus" "amābatis" "amābant"] amo :conjugation-1 :imperf :ind :act)
  (assert-forms ["amārem" "amārēs" "amāret" "amārēmus" "amārētis" "amārent"] amo :conjugation-1 :imperf :subj :act)
  (assert-forms ["amābar" "amābāris" "amābātur" "amābāmur" "amābāminī" "amābantur" "amābāre"] amo :conjugation-1 :imperf :ind :pass)
  (assert-forms ["amārer" "amārēris" "amārētur" "amārēmur" "amārēminī" "amārentur" "amarēre"] amo :conjugation-1 :imperf :subj :pass)

  (assert-forms ["dēlēbam" "dēlēbās" "dēlēbat" "dēlēbāmus" "dēlābātis" "dēlēbant"] deleo :conjugation-2 :imperf :ind :act)
  (assert-forms ["dēlērem" "dēlērēs" "dēlēret" "dēlērēmus" "dēlērētis" "dēlērent"] deleo :conjugation-2 :imperf :subj :act)
  (assert-forms ["dēlēbar" "dēlēbāris" "dēlēbātur" "dēlēbāmur" "dēlēbāminī" "dēlēbantur" "dēlēbāre"] deleo :conjugation-2 :imperf :ind :pass)
  (assert-forms ["dēlērer" "dēlērēris" "dēlērētur" "dēlērēmur" "dēlērēmini" "dēlērentur" "dēlērēre"] deleo :conjugation-2 :imperf :subj :pass)

  (assert-forms ["dūcēbam" "dūcēbās" "dūcābat" "dūcēbāmus" "dūcābātis" "dūcēbant"] duco :conjugation-3 :imperf :ind :act)
  (assert-forms ["dūcerem" "dūcerēs" "dūceret" "dūcerēmus" "dūcerētis" "dūcerent"] duco :conjugation-3 :imperf :subj :act)
  (assert-forms ["dūcēbar" "dūcēbāris" "dūcēbātur" "dūcēbāmur" "dūcēbāminī" "dūcēbantur" "dūcēbāre"] duco :conjugation-3 :imperf :ind :pass)
  (assert-forms ["dūcerer" "dūcerēris" "dūcerētur" "dūcerēmur" "dūcerēminī" "dūcerentur" "dūcerēre"] duco :conjugation-3 :imperf :subj :pass)

  (assert-forms ["audiēbam" "audiēbās" "audiēbat" "audiēbāmus" "audiēbātis" "audiēbant"] audio :conjugation-4 :imperf :ind :act)
  (assert-forms ["audīrem" "audīrēs" "audīret" "audīrēmus" "audīrētis" "audīrent"] audio :conjugation-4 :imperf :subj :act)
  (assert-forms ["audiēbar" "audiēbāris" "audiēbātur" "audiēbāmur" "audiēbāminī" "audiēbantur" "audiēbāre"] audio :conjugation-4 :imperf :ind :pass)
  (assert-forms ["audīrer" "audīrēris" "audīrētur" "audīrēmur" "audīrēminī" "audīrentur" "audīrēre"] audio :conjugation-4 :imperf :subj :pass)

  (assert-forms ["capiēbam" "capiēbās" "capiēbat" "capiēbāmus" "capiēbātis" "capiēbant"] capio :conjugation-5 :imperf :ind :act)
  (assert-forms ["caperem" "caperēs" "caperet" "caperēmus" "caperētis" "caperent"] capio :conjugation-5 :imperf :subj :act)
  (assert-forms ["capiēbar" "capiēbāris" "capiēbātur" "capiēbāmur" "capiēbāminī" "capiēbantur" "capiēbāre"] capio :conjugation-5 :imperf :ind :pass)
  (assert-forms ["caperer" "caperēris" "caperētur" "caperēmur" "caperēminī" "caperentur" "caperēre"] capio :conjugation-5 :imperf :subj :pass)
  ;;
  ;; FUTURE SYSTEM
  ;;
  (assert-forms ["amābō" "amābis" "amābit" "amābīmus" "amābitits" "amābunt"] amo :conjugation-1 :fut :ind :act)
  (assert-forms ["amābor" "amāberis" "amābitur" "amābimur" "amābiminī" "amābuntur"] amo :conjugation-1 :fut :ind :pass)

  (assert-forms ["dēlēbō" "dēlēbis" "dēlēbit" "dēlēbimus" "dēlēbitis" "dēlēbunt"] deleo :conjugation-2 :fut :ind :act)
  (assert-forms ["dēlēbor" "dēlēberis" "dēlēbitur" "dēlēbimur" "dēlēbiminī" "dēlēbuntur" "dēlēbere" nil] deleo :conjugation-2 :fut :ind :pass)

  (assert-forms ["dūcam" "dūcēs" "dūcet" "dūcēmus" "dūcētis" "dūcent"] duco :conjugation-3 :fut :ind :act)
  (assert-forms ["dūcar" "dūcēris" "dūcētur" "dūcēmur" "dūcēminī" "dūcentur" "dūcēre" nil] duco :conjugation-3 :fut :ind :pass)

  (assert-forms ["audiam" "audiēs" "audiet" "audiēmus" "audiētis" "audient"] audio :conjugation-4 :fut :ind :act)
  (assert-forms ["audiar" "audiēris" "audiētur" "audiēmur" "audiēminī" "audientur" "audiēre" nil] audio :conjugation-4 :fut :ind :pass)

  (assert-forms ["capiam" "capiēs" "capiet" "capiēmus" "capiētis" "capient"] capio :conjugation-5 :fut :ind :act)
  (assert-forms ["capiar" "capiēris" "capiētur" "capiēmur" "capiēminī" "capientur" "capiēre" nil] capio :conjugation-5 :fut :ind :pass)
  ;;
  ;; PERFECT SYSTEM
  ;;
  (assert-forms ["amāuī" "amāuistī" "amāuit" "amāuimus" "amāuistis" "amāuērunt" "amāuēre"] amo :conjugation-1 :perf :ind :act)
  (assert-forms ["amāuerim" "amāuerīs" "amāuerit" "amāuerīmus" "amāuerītis" "amāuerint"] amo :conjugation-1 :perf :subj :act)
  (assert-forms ["amātus sum" "amātus es" "amātus est" "amātī sumus" "amātī estis" "amātī sunt"] amo :conjugation-1 :perf :ind :pass)
  (assert-forms ["amātus sim" "amātus sīs" "amātus sit" "amātī sīmus" "amātī sītis" "amātī sint"] amo :conjugation-1 :perf :subj :pass)


  (assert-forms ["dēlēuī" "dēlēuistī" "dēlēuit" "dēlēuimus" "dēlēuistis" "dēlēuērunt" "dēlēuēre"] deleo :conjugation-2 :perf :ind :act)
  (assert-forms ["dēlēuerim" "dēlēuerīs" "dēlēuerit" "dēlēuerīmus" "dēlēuerītis" "dēlēuerint"] deleo :conjugation-2 :perf :subj :act)
  (assert-forms ["dēlētus sum" "dēlētus es" "dēlētus est" "dēlētī sumus" "dēlētī estis" "dēlētī sunt"] deleo :conjugation-2 :perf :ind :pass)
  (assert-forms ["dēlētus sim" "dēlētus sīs" "dēlētus sit" "dēlētī sīmus" "dēlētī sītis" "dēlētī sint"] deleo :conjugation-2 :perf :subj :pass)

  (assert-forms ["dūxī" "dūxistī" "dūxit" "dūximus" "dūxistis" "dūxērunt" "dūxēre"] duco :conjugation-3 :perf :ind :act)
  (assert-forms ["dūxerim" "dūxerīs" "dūxerit" "dūxerīmus" "dūxerītis" "dūxerint"] duco :conjugation-3 :perf :subj :act)
  (assert-forms ["ductus sum" "ductus es" "ductus est" "ductī sumus" "ductī estis" "ductī sunt"] duco :conjugation-3 :perf :ind :pass)
  (assert-forms ["ductus sim" "ductus sīs" "ductus sit" "ductī sīmus" "ductī sītis" "ductī sint"] duco :conjugation-3 :perf :subj :pass)

  (assert-forms ["audīuī" "audīuistī" "audīuit" "audīuimus" "audīuistis" "audīuērunt" "audīuēre"] audio :conjugation-4 :perf :ind :act)
  (assert-forms ["audīuerim" "audīuerīs" "audīuerit" "audīuerīmus" "audīuerītis" "audīuerint"] audio :conjugation-4 :perf :subj :act)
  (assert-forms ["audītus sum" "audītus es" "audītus est" "audītī sumus" "audītī estis" "audītī sunt"] audio :conjugation-4 :perf :ind :pass)
  (assert-forms ["audītus sim" "audītus sīs" "audītus sit" "audītī sīmus" "audītī sītis" "audītī sint"] audio :conjugation-4 :perf :subj :pass)

  (assert-forms ["cēpī" "cēpistī" "cēpit" "cēpimus" "cēpistis" "cēpērunt" "cēpēre"] capio :conjugation-5 :perf :ind :act)
  (assert-forms ["cēperim" "cēperīs" "cēperit" "cēperīmus" "cēperītis" "cēperint"] capio :conjugation-5 :perf :subj :act)
  (assert-forms ["captus sum" "captus es" "captus est" "captī sumus" "captī estis" "captī sunt"] capio :conjugation-5 :perf :ind :pass)
  (assert-forms ["captus sim" "captus sīs" "captus sit" "captī sīmus" "captī sītis" "captī sint"] capio :conjugation-5 :perf :subj :pass)
  ;;
  ;; PLUPERFECT SYSTEM
  ;;
  (assert-forms ["amāueram" "amāuerās" "amāuerat" "amāuerāmus" "amāuerātis" "amāuerant"] amo :conjugation-1 :plup :ind :act)
  (assert-forms ["amāuissem" "amāuissēs" "amāuisset" "amāuissēmus" "amāuissētis" "amāuissent"] amo :conjugation-1 :plup :subj :act)
  (assert-forms ["amātus eram" "amātus erās" "amātus erat" "amātī erāmus" "amātī erātis" "amātī erant"] amo :conjugation-1 :plup :ind :pass)
  (assert-forms ["amātus essem" "amātus essēs" "amātus esset" "amātī essēmus" "amātī essētis" "amātī essent"] amo :conjugation-1 :plup :subj :pass)

  (assert-forms ["dēlēueram" "dēlēuerās" "dēlēuerat" "dēlēuerāmus" "dēlēuerātis" "dēlēuerant"] deleo :conjugation-2 :plup :ind :act )
  (assert-forms ["dēlēuissem" "dēlēuissēs" "dēlēuisset" "dēlēuissēmus" "dēlēuissētis" "dēlēuissent"] deleo :conjugation-2 :plup :subj :act)
  (assert-forms ["dēlētus eram" "dēlētus erās" "dēlētus erat" "dēlētī erāmus" "dēlētī erātis" "dēlētī erant"] deleo :conjugation-2 :plup :ind :pass)
  (assert-forms ["dēlētus essem" "dēlētus essēs" "dēlētus esset" "dēlētī essēmus" "dēlētī essētis" "dēlētī essent"] deleo :conjugation-2 :plup :subj :pass)

  (assert-forms ["dūxeram" "dūxerās" "dūxerat" "dūxerāmus" "dūxerātis" "dūxerant"] duco :conjugation-3 :plup :ind :act)
  (assert-forms ["dūxissem" "dūxissēs" "dūxisset" "dūxissēmus" "dūxissētis" "dūxissent"] duco :conjugation-3 :plup :subj :act)
  (assert-forms ["ductus eram" "ductus erās" "ductus erat" "ductī erāmus" "ductī erātis" "ductī erant"] duco  :conjugation-3 :plup :ind :pass)
  (assert-forms ["ductus essem" "ductus essēs" "ductus esset" "ductī essēmus" "ductī essētis" "ductī essent"] duco :conjugation-3 :plup :subj :pass)

  (assert-forms ["audīueram" "audīuerās" "audīuerat" "audīuerāmus" "audīuerātis" "audīuerant"] audio :conjugation-4 :plup :ind :act)
  (assert-forms ["audīuissem" "audīuissēs" "audīuisset" "audīuissēmus" "audīuissētis" "audīuissent"] audio :conjugation-4 :plup :subj :act)
  (assert-forms ["audītus eram" "audītus erās" "audītus erat" "audītī erāmus" "audītī erātis" "audītī erant"] audio :conjugation-4 :plup :ind :pass)
  (assert-forms ["audītus essem" "audītus essēs" "audītus esset" "audītī essēmus" "audītī essētis" "audītī essent"] audio :conjugation-4 :plup :subj :pass)

  (assert-forms ["cēperam" "cēperās" "cēperat" "cēperāmus" "cēperātis" "cēperant"] capio :conjugation-5 :plup :ind :act)
  (assert-forms ["cēpissem" "cēpissēs" "cēpisset" "cēpissēmus" "cēpissētis" "cēpissent"] capio :conjugation-5 :plup :subj :act)
  (assert-forms ["captus eram" "captus erās" "captus erat" "captī erāmus" "captī erātis" "captī erant"] capio :conjugation-5 :plup :ind :pass)
  (assert-forms ["captus essem" "captus essēs" "captus esset" "captī essēmus" "captī essētis" "captī essent"] capio :conjugation-5 :plup :subj :pass)
  ;;
  ;; FUTURE PERFECT SYSTEM
  ;;
  (assert-forms ["amāuerō" "amāueris" "amāuerit" "amāuerimus"  "amāueritis" "amāuerint"] amo :conjugation-1 :futperf :ind :act)
  (assert-forms ["amātus erō" "amātus eris" "amātus erit" "amātī erimus" "amātī eritis" "amātī erunt"] amo :conjugation-1 :futperf :ind :pass)

  (assert-forms ["dēlēuerō" "dēlēueris" "dēlēuerit" "dēlēuerimus"  "dēlēueritis" "dēlēuerint"] deleo :conjugation-2 :futperf :ind :act )
  (assert-forms ["dēlētus erō" "dēlētus eris" "dēlētus erit" "dēlētī erimus" "dēlētī eritis" "dēlētī erunt"] deleo :conjugation-2 :futperf :ind :pass)

  (assert-forms ["dūxerō" "dūxeris" "dūxerit" "dūxerimus"  "dūxeritis" "dūxerint"] duco :conjugation-3 :futperf :ind :act)
  (assert-forms ["ductus erō" "ductus eris" "ductus erit" "ductī erimus" "ductī eritis" "ductī erunt"] duco :conjugation-3 :futperf :ind :pass)

  (assert-forms ["audīuerō" "audīueris" "audīuerit" "audīuerimus"  "audīueritis" "audīuerint"] audio :conjugation-4 :futperf :ind :act)
  (assert-forms ["audītus erō" "audītus eris" "audītus erit" "audītī erimus" "audītī eritis" "audītī erunt"] audio :conjugation-4 :futperf :ind :pass)

  (assert-forms ["cēperō" "cēperis" "cēperit" "cēperimus"  "cēperitis" "cēperint"] capio :conjugation-5 :futperf :ind :act)
  (assert-forms ["captus erō" "captus eris" "captus erit" "captī erimus" "captī eritis" "captī erunt"] capio :conjugation-5 :futperf :ind :pass)

  )

