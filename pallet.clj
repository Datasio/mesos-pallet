;;; Pallet project configuration file

(require
 '[mesos-pallet.groups.mesos-pallet :refer [mesos-master mesos-slaves]])

(defproject mesos-pallet
  :provider {:jclouds {}}

  :groups [mesos-master mesos-slaves])
