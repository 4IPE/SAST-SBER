'use client'

import { useState } from 'react'
import Link from 'next/link'
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card"
import { Button } from "@/components/ui/button"
import { Users2, ChevronRight, FolderGit2 } from 'lucide-react'

const testTeams = [
  { id: 1, name: "Frontend Developers", membersCount: 5, project: "E-commerce Platform", projectId: 1 },
  { id: 2, name: "Backend Engineers", membersCount: 4, project: "Banking App", projectId: 2 },
  { id: 3, name: "QA Team", membersCount: 3, project: "Social Media Dashboard", projectId: 3 },
  { id: 4, name: "DevOps", membersCount: 2, project: "Healthcare Management System", projectId: 4 },
]

export default function Teams() {
  const [teams] = useState(testTeams)

  return (
    <div className="container mx-auto px-4 py-8 animate-fade-in">
      <h1 className="text-4xl font-bold mb-8 bg-gradient-to-r from-primary to-secondary bg-clip-text text-transparent">Teams</h1>
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        {teams.map((team) => (
          <Card key={team.id} className="bg-card hover:bg-card-hover transition-all duration-300 transform hover:-translate-y-1">
            <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
              <CardTitle className="text-xl font-bold text-text-primary">{team.name}</CardTitle>
              <Users2 className="h-5 w-5 text-text-secondary" />
            </CardHeader>
            <CardContent>
              <p className="text-sm text-text-secondary mb-1">Members: {team.membersCount}</p>
              <p className="text-sm text-text-secondary mb-4">Project: {team.project}</p>
              <div className="flex space-x-2">
                <Link href={`/teams/${team.id}`} className="flex-1">
                  <Button variant="outline" className="w-full group">
                    View Team
                    <ChevronRight className="ml-2 h-4 w-4 transition-transform group-hover:translate-x-1" />
                  </Button>
                </Link>
                <Link href={`/projects/${team.projectId}`} className="flex-1">
                  <Button variant="outline" className="w-full group">
                    View Project
                    <FolderGit2 className="ml-2 h-4 w-4" />
                  </Button>
                </Link>
              </div>
            </CardContent>
          </Card>
        ))}
      </div>
    </div>
  )
}

