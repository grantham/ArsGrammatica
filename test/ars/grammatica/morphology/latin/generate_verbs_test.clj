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
        [clojure.test]))

(deftest find-endings-test
  (is (not (nil? (find-endings :conjugation-1 :pres :ind :act))))
  (is (not (nil? (find-endings :conjugation-2 :pres :ind :act))))
  (is (not (nil? (find-endings :conjugation-3 :pres :ind :act))))
  (is (not (nil? (find-endings :conjugation-4 :pres :ind :act))))
  (is (not (nil? (find-endings :conjugation-5 :pres :ind :act))))
  )
