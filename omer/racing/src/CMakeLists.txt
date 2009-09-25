#racing-view
cmake_minimum_required(VERSION 2.6)

set(MOC_HEADERS QCircuitView.h)
qt_wrap_cpp(racing MOC_FILES ${MOC_HEADERS})

set(VIEW_FILES mainview.cc QCircuitView.cc QCircuitView.h corbes.cc corbes.h common.h)
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
