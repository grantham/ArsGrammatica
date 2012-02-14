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
  ars.grammatica.tokenizer-test
  (:use [clojure.test]
        [ars.grammatica.parse.tokenizer]))


(deftest tokenize-test
 ;; (let [tokens (tokenize :greek "μῆνιν ἄειδε θεὰ")]
 ;;   (is (= 3 (count tokens))))
  (let [tokens (tokenize :latin
    "Arma virumque cano, Troiae qui primus ab oris
     Italiam, fato profugus, Laviniaque venit
     litora, multum ille et terris iactatus et alto
     vi superum saevae memorem Iunonis ob iram;")]
     (is (= 33 (count tokens)))
     (is (= "uirumque" (:form (nth tokens 1))))))

