Learning1 demonstrates how livedata replaces callbacks in situations where we also store the data and notify listeners after that.
[mValue = value]
[notifyListeners()]

it also internally handles all the required thread synchronisation.