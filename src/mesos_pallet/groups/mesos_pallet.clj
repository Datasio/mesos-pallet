(ns mesos-pallet.groups.mesos-pallet
  "Node defintions for mesos-pallet"
  (:require
   [pallet.api :refer [group-spec server-spec node-spec plan-fn]]
   [pallet.crate.automated-admin-user :refer [automated-admin-user]]
   [pallet.crate.mesos :refer [mesos-install mesos-master-config mesos-slave-config]]))


(def with-base
  (server-spec
   :phases
   {:bootstrap (plan-fn (automated-admin-user))
    :configure (plan-fn (mesos-install))}))

(def with-master
  (server-spec
   :phases {:configure (plan-fn (mesos-master-config))}))

(def with-slave
  (server-spec
   :phases {:configure (plan-fn (mesos-slave-config))}))


(def mesos-master
  (group-spec "mesos-master" :extends [with-base with-master]
              :node-spec (node-spec :image {:os-family :ubuntu :os-version-matches "12.04" :os-64-bit true}
                                    :location {:location-id "us-east-1"}
                                    :hardware {:hardware-id "m1.medium"}
                                    )
              :count 1
              :roles #{:master :zookeeper}))

(def mesos-slaves
  (group-spec "mesos-slaves" :extends [with-base with-slave]
              :node-spec (node-spec :image {:os-family :ubuntu :os-version-matches "12.04" :os-64-bit true}
                                    :location {:location-id "us-east-1"}
                                    :hardware {:hardware-id "m1.medium"}
                                    )
              :count 1
              :roles #{:slave}))
