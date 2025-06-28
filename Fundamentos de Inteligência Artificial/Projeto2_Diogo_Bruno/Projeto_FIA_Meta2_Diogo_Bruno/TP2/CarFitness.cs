using GeneticSharp.Domain.Fitnesses;
using GeneticSharp.Domain.Chromosomes;
using System.Threading;
using UnityEngine;
using System.Collections.Generic;
using System.Collections.Concurrent;
using System;
using System.Linq;

namespace GeneticSharp.Runner.UnityApp.Car
{
    public class CarFitness : IFitness
    {
        // Forma mais rapida
        public CarFitness()
        {
            ChromosomesToBeginEvaluation = new BlockingCollection<CarChromosome>();
            ChromosomesToEndEvaluation = new BlockingCollection<CarChromosome>();
        }

        public BlockingCollection<CarChromosome> ChromosomesToBeginEvaluation { get; private set; }
        public BlockingCollection<CarChromosome> ChromosomesToEndEvaluation { get; private set; }

        private float calcFitness(float Distance, float EllapsedTime, float SumVelocities)
        {
            // Prioridade ao tempo e distancia
            float fitness = Distance / EllapsedTime * 0.8f + SumVelocities * 0.2f;   
            return fitness;
        }

        // Forma mais eficiente
        private float calcFitness2(float Distance, float SumVelocities, float SumTotalForces, int RoadCompleted)
        {
            if(RoadCompleted == 1){
                // Prioridade à distancia e forças

                float efficiency = (Distance * 100) + 10f;

                float energyEfficiency =  1 / (SumTotalForces);

                float fitness = (efficiency) * (energyEfficiency);

                return fitness;
            }
            else{
                // Prioridade à distancia
                float fitness = (Distance /10000) * 0.8f + (SumVelocities/1000) * 0.2f;

                return fitness;
            }
        }

        public double Evaluate(IChromosome chromosome)
        {
            var c = chromosome as CarChromosome;
            ChromosomesToBeginEvaluation.Add(c);

            float fitness = 0; 
            do
            {
                Thread.Sleep(1000);

                
                float Distance = c.Distance;
                float EllapsedTime = c.EllapsedTime;
                float NumberOfWheels = c.NumberOfWheels;
                float CarMass = c.CarMass;
                int RoadCompleted = c.RoadCompleted ? 1 : 0;

                List<float> Velocities = c.Velocities;
                float SumVelocities = c.SumVelocities;
                
                List<float> Accelerations = c.Accelerations;
                float SumAccelerations = c.SumAccelerations;

                List<float> Forces = c.Forces;
                float SumTotalForces = c.SumForces;

                /*YOUR CODE HERE*/
                /*Note que é executado ao longo da simulação*/
                if (RoadCompleted == 1){
                    //fitness = calcFitness(Distance, EllapsedTime, SumVelocities);
                    fitness = calcFitness2(Distance, SumVelocities, SumTotalForces, RoadCompleted);
                }
                else if (RoadCompleted == 0 && Distance > 0){
                    //fitness = calcFitness(Distance, EllapsedTime, SumVelocities);
                    fitness = calcFitness2(Distance, SumVelocities, SumTotalForces, RoadCompleted);
                }
                else{
                    fitness = 0;
                }
                /*END OF YOUR CODE*/

                c.Fitness = fitness;

            } while (!c.Evaluated);

            ChromosomesToEndEvaluation.Add(c);


            do
            {
                Thread.Sleep(1000);
            } while (!c.Evaluated);

            /*O valor da variável fitness é o valor de aptidão do indivíduo*/

            return fitness;
        }

    }
}