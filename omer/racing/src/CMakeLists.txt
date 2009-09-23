#racing-view

set(MOC_HEADERS Qview.h)
qt_wrap_cpp(racing MOC_FILES ${MOC_HEADERS})

set(VIEW_FILES mainview.cc Qview.cc Qview.h bezier.cc bezier.h common.h)
add_executable(racing-view ${VIEW_FILES} ${MOC_FILES})

target_link_libraries(racing-view ${QT_LIBRARIES})

install(TARGETS racing-view RUNTIME DESTINATION bin)


#racing-sim
#
#set(SIM_FILES sim.cc
#  car.cc car.h
#  bezier.cc bezier.h common.h)
#add_executable(racing-sim ${SIM_FILES})
#
#install(TARGETS racing-sim RUNTIME DESTINATION bin)
